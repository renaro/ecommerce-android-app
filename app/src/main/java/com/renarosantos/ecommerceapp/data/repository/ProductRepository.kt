package com.renarosantos.ecommerceapp.data.repository

import com.renarosantos.ecommerceapp.business.Product
import com.renarosantos.ecommerceapp.business.ProductDetails

interface ProductRepository {

    suspend fun getProductList(): List<Product>

    suspend fun getProductDetails(productId: String): ProductDetails
}