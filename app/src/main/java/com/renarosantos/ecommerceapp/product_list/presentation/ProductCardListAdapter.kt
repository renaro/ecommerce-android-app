package com.renarosantos.ecommerceapp.product_list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.renarosantos.ecommerceapp.R
import com.renarosantos.ecommerceapp.databinding.ProductCardBinding

class ProductCardListAdapter(
    val onItemClicked: (ProductCardViewState) -> Unit,
    val onFavoriteIconClicked: (ProductCardViewState) -> Unit,
    val onBuyCLicked: (ProductCardViewState) -> Unit,
    val onRemoveClicked: (ProductCardViewState) -> Unit,
) : RecyclerView.Adapter<ProductCardListAdapter.ViewHolder>() {


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
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(productCardViewState: ProductCardViewState) {
            val bind = ProductCardBinding.bind(itemView)
            itemView.setOnClickListener {
                onItemClicked(productCardViewState)
            }
            bind.apply {
                viewProductName.text = productCardViewState.title
                viewProductDescription.text = productCardViewState.description
                productPrice.text = productCardViewState.price

                viewWishlistIcon.setOnClickListener {
                    onFavoriteIconClicked.invoke(
                        productCardViewState
                    )
                }
                buyButton.setOnClickListener {
                    onBuyCLicked.invoke(productCardViewState)
                }
                removeButton.setOnClickListener {
                    onRemoveClicked.invoke(productCardViewState)
                }
                buyButton.isInvisible = productCardViewState.isProductInCart
                removeButton.isInvisible = !productCardViewState.isProductInCart
                viewWishlistIcon.setImageDrawable(
                    if (productCardViewState.isFavorite) {
                        ResourcesCompat.getDrawable(
                            viewWishlistIcon.resources,
                            R.drawable.ic_favorite_enabled,
                            null
                        )
                    } else {
                        ResourcesCompat.getDrawable(
                            viewWishlistIcon.resources,
                            R.drawable.ic_favorite_disabled,
                            null
                        )
                    }
                )
                Glide.with(productImage)
                    .asBitmap()
                    .load(productCardViewState.imageUrl)
                    .into(BitmapImageViewTarget(productImage))
            }
        }

    }
}