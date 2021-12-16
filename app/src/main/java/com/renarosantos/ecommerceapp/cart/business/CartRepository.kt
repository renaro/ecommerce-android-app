package com.renarosantos.ecommerceapp.cart.business

import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun observeChanges(): Flow<List<String>>
    suspend fun addToCart(productId: String) : AddToCartResult

    sealed class AddToCartResult {
        object Success : AddToCartResult()
        object Error : AddToCartResult()
    }
}