package com.renarosantos.ecommerceapp.product_list.presentation

data class ProductCardViewState(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    val imageUrl : String,
    val isFavorite : Boolean,
    val isProductInCart : Boolean
)