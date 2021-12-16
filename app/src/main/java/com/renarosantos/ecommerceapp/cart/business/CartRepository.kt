package com.renarosantos.ecommerceapp.cart.business

import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun observeChanges(): Flow<List<String>>
    suspend fun addToCart(productId: String)
    suspend fun removeFromCart(id: String)
}