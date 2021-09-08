package com.renarosantos.ecommerceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renarosantos.ecommerceapp.databinding.ProductCardBinding

class ProductCardListAdapter : RecyclerView.Adapter<ProductCardListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ProductCardViewState("Playstation", "This is a nice console! Check it out", "200 US$"))
    }

    override fun getItemCount(): Int {
      return 3
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