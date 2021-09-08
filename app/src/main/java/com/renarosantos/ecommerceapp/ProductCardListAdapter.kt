package com.renarosantos.ecommerceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renarosantos.ecommerceapp.databinding.ProductCardBinding

class ProductCardListAdapter : RecyclerView.Adapter<ProductCardListAdapter.ViewHolder>() {


    private var data: List<ProductCardViewState> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
      return data.size
    }

    fun setData(productList: List<ProductCardViewState>) {
        this.data = productList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(productCardViewState: ProductCardViewState) {
            val bind =  ProductCardBinding.bind(itemView)
            bind.viewProductName.text = productCardViewState.title
            bind.viewProductDescription.text = productCardViewState.description
            bind.productPrice.text = productCardViewState.price
        }

    }
}