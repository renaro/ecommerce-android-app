package com.renarosantos.ecommerceapp.presentation.viewstate

import com.renarosantos.ecommerceapp.business.ProductDetails

sealed class ProductDetailsViewState {
    object Loading : ProductDetailsViewState()
    data class Content(val product: ProductDetails) : ProductDetailsViewState()
    object Error : ProductDetailsViewState()
}