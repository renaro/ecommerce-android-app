package com.renarosantos.ecommerceapp

sealed class ProductListViewState {
    object Loading : ProductListViewState()
    object Error : ProductListViewState()
    data class Content(val productList: List<ProductCardViewState>) : ProductListViewState()
}