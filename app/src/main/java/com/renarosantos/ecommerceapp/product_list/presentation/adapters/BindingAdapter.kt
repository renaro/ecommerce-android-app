package com.renarosantos.ecommerceapp.product_list.presentation.adapters

import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pixplicity.easyprefs.library.Prefs
import com.renarosantos.ecommerceapp.R
import com.renarosantos.ecommerceapp.product_list.utils.Utils.PREFS_LAYOUT_MANAGER_SPAN_COUNT_1
import com.renarosantos.ecommerceapp.product_list.utils.Utils.PREFS_LAYOUT_MANAGER_SPAN_COUNT_2
import com.renarosantos.ecommerceapp.product_list.utils.Utils.PREFS_LAYOUT_MANAGER_SPAN_COUNT_KEY

@BindingAdapter("handleToggle")
fun ImageView.handleLayoutToggle(recyclerView: RecyclerView) {
    val gridLayoutDrawable = ContextCompat.getDrawable(this.context, R.drawable.ic_grid_layout)
    val linearLayoutDrawable =
        ContextCompat.getDrawable(this.context, R.drawable.ic_linear_layout)
    setOnClickListener {
        val currentSpanCount = Prefs.getInt(PREFS_LAYOUT_MANAGER_SPAN_COUNT_KEY, 0)
        if (currentSpanCount == PREFS_LAYOUT_MANAGER_SPAN_COUNT_2 || currentSpanCount == 0) {
            setSpanCount(recyclerView, PREFS_LAYOUT_MANAGER_SPAN_COUNT_1)
            this.setImageDrawable(gridLayoutDrawable)
        } else {
            setSpanCount(recyclerView, PREFS_LAYOUT_MANAGER_SPAN_COUNT_2)
            setImageDrawable(linearLayoutDrawable)
        }
    }
}

fun setSpanCount(recyclerView: RecyclerView, preferedSpanCount: Int) {
    Prefs.putInt(PREFS_LAYOUT_MANAGER_SPAN_COUNT_KEY, preferedSpanCount)
    val staggeredGridLayoutManager =
        (recyclerView.layoutManager as StaggeredGridLayoutManager).apply {
            spanCount = preferedSpanCount
        }
    recyclerView.layoutManager = staggeredGridLayoutManager
}
