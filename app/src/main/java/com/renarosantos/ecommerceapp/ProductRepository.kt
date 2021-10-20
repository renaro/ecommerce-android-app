package com.renarosantos.ecommerceapp

interface ProductRepository {

    suspend fun getProductList(): List<ProductCardViewState>

    suspend fun getProductDetails(productId: String): ProductDetails
}