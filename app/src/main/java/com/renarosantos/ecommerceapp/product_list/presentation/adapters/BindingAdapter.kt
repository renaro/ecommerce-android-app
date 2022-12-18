package com.renarosantos.ecommerceapp.product_list.presentation.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pixplicity.easyprefs.library.Prefs
import com.renarosantos.ecommerceapp.product_list.utils.Utils.PREFS_LAYOUT_MANAGER_SPAN_COUNT_1
import com.renarosantos.ecommerceapp.product_list.utils.Utils.PREFS_LAYOUT_MANAGER_SPAN_COUNT_2
import com.renarosantos.ecommerceapp.product_list.utils.Utils.PREFS_LAYOUT_MANAGER_SPAN_COUNT_KEY

@BindingAdapter("handleToggle")
fun ImageView.handleLayoutToggle(recyclerView: RecyclerView) {
    setOnClickListener {
        val currentSpanCount = Prefs.getInt(PREFS_LAYOUT_MANAGER_SPAN_COUNT_KEY, 0)
        if (currentSpanCount == PREFS_LAYOUT_MANAGER_SPAN_COUNT_2) {
            setSpanCount(recyclerView, PREFS_LAYOUT_MANAGER_SPAN_COUNT_1)
        } else {
            setSpanCount(recyclerView, PREFS_LAYOUT_MANAGER_SPAN_COUNT_2)
        }
    }
}

fun setSpanCount(recyclerView: RecyclerView, preferedSpanCount: Int) {
    val staggeredGridLayoutManager =
        (recyclerView.layoutManager as StaggeredGridLayoutManager).apply {
            spanCount = preferedSpanCount
        }
    recyclerView.layoutManager = staggeredGridLayoutManager
}