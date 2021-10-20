package com.renarosantos.ecommerceapp.shared.data.repository

import com.renarosantos.ecommerceapp.product_list.business.Product
import com.renarosantos.ecommerceapp.product_details.business.ProductDetails

interface ProductRepository {

    suspend fun getProductList(): List<Product>

    suspend fun getProductDetails(productId: String): ProductDetails
}