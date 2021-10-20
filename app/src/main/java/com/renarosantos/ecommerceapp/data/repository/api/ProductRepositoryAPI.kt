package com.renarosantos.ecommerceapp.data.repository.api

import com.renarosantos.ecommerceapp.business.Product
import com.renarosantos.ecommerceapp.presentation.viewstate.ProductCardViewState
import com.renarosantos.ecommerceapp.business.ProductDetails
import com.renarosantos.ecommerceapp.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryAPI @Inject constructor(private val service: ProductService) :
    ProductRepository {

    override suspend fun getProductList(): List<Product> {
        return withContext(Dispatchers.IO) {
            service.getProductList().map {
                Product(
                    it.title,
                    it.description,
                    it.price,
                    it.imageUrl
                )
            }
        }
    }

    override suspend fun getProductDetails(productId: String): ProductDetails {
        return withContext(Dispatchers.IO) {
            service.getProductDetails(productId).run {
                ProductDetails(
                    this.title,
                    this.description,
                    this.full_description,
                    "US $ ${this.price}",
                    this.imageUrl,
                    this.pros,
                    this.cons
                )
            }
        }
    }
}