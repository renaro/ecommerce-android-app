package com.renarosantos.ecommerceapp.shared.data.repository.api

import com.renarosantos.ecommerceapp.product_list.business.Product
import com.renarosantos.ecommerceapp.product_details.business.ProductDetails
import com.renarosantos.ecommerceapp.shared.data.repository.ProductRepository
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
                    it.imageUrl,
                    it.id
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