package com.renarosantos.ecommerceapp

sealed class ProductListViewState {
    object Loading : ProductListViewState()
    data class Content(val productList: List<ProductCardViewState>) : ProductListViewState()
    data class Error(val errorMessage: String) : ProductListViewState()
}