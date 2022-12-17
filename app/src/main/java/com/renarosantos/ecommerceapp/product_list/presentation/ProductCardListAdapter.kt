package com.renarosantos.ecommerceapp.product_list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.renarosantos.ecommerceapp.R
import com.renarosantos.ecommerceapp.databinding.ProductCardBinding
import com.renarosantos.ecommerceapp.databinding.ViewTypeTextLayoutBinding
import com.renarosantos.ecommerceapp.product_list.utils.Utils.VIEW_TYPE_PRODUCTS
import com.renarosantos.ecommerceapp.product_list.utils.Utils.VIEW_TYPE_TEXT

class ProductCardListAdapter(
    val onItemClicked: (ProductCardViewState) -> Unit,
    val onFavoriteIconClicked: (ProductCardViewState) -> Unit,
    val onBuyCLicked: (ProductCardViewState) -> Unit,
    val onRemoveClicked: (ProductCardViewState) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<ProductCardViewState> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == 0) ViewHolderTypeText(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_type_text_layout, parent, false)
        ) else {
            ViewHolderTypeProduct(
                LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) (holder as ViewHolderTypeText).bind(data.size) else (holder as ViewHolderTypeProduct).bind(
            data[position]
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(productList: List<ProductCardViewState>) {
        val products = arrayListOf<ProductCardViewState>()
        products.apply {
            add(0, ProductCardViewState("", "", "", "", "", false, false))
            addAll(productList)
        }
        this.data = products
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_TEXT else VIEW_TYPE_PRODUCTS
    }

    inner class ViewHolderTypeText(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemCount: Int) {
            val binding = ViewTypeTextLayoutBinding.bind(itemView)
            binding.apply {
                val numberOfItems = if (itemCount > 0) itemCount - 1 else 0
                textView.text = binding.root.resources.getQuantityString(
                    R.plurals.product_item_count,
                    numberOfItems,
                    numberOfItems
                )
            }
        }
    }

    inner class ViewHolderTypeProduct(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                            R.drawable.ic_baseline_favorite,
                            null
                        )
                    } else {
                        ResourcesCompat.getDrawable(
                            viewWishlistIcon.resources,
                            R.drawable.ic_baseline_favorite_disabled,
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
