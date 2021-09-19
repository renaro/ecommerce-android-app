package com.renarosantos.ecommerceapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ProductRepository {

    private val client = ApiClient().getClient()
    suspend fun getProductList(): List<ProductCardViewState> {
        return withContext(Dispatchers.IO) {
            client.getProductList().map {
                ProductCardViewState(
                    it.title,
                    it.description,
                    "US $ ${it.price}",
                    it.imageUrl
                )
            }
        }
    }
}