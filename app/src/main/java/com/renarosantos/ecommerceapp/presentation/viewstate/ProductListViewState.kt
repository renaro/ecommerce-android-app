package com.renarosantos.ecommerceapp.presentation.viewstate

sealed class ProductListViewState {
    object Loading : ProductListViewState()
    data class Content(val productList: List<ProductCardViewState>) : ProductListViewState()
    object Error : ProductListViewState()
}