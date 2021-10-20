package com.renarosantos.ecommerceapp

sealed class ProductDetailsViewState {
    object Loading : ProductDetailsViewState()
    data class Content(val product: ProductDetails) : ProductDetailsViewState()
    object Error : ProductDetailsViewState()
}