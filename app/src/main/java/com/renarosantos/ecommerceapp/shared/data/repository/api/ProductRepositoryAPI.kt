package com.renarosantos.ecommerceapp.shared.data.repository.api

import android.util.Log
import com.renarosantos.ecommerceapp.product_details.business.ProductDetails
import com.renarosantos.ecommerceapp.product_list.business.Product
import com.renarosantos.ecommerceapp.shared.business.ProductRepository
import com.renarosantos.ecommerceapp.shared.business.Result
import com.renarosantos.ecommerceapp.shared.business.Result.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryAPI @Inject constructor(private val service: ProductService) :
    ProductRepository {

    override suspend fun getProductList(): Result<List<Product>> {
        return withContext(Dispatchers.IO) {
            try {
                val data = service.getProductList().map {
                    Product(
                        it.title,
                        it.description,
                        it.price,
                        it.imageUrl,
                        it.id
                    )
                }
                if (!data.isEmpty()) {
                    Success(data)
                } else {
                    Error(IllegalStateException("Empty product list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayer", exception.message, exception)
                Error(exception)
            }
        }
    }

    override suspend fun getProductDetails(productId: String): Result<ProductDetails> {
        return withContext(Dispatchers.IO) {
            try {
                service.getProductDetails(productId).run {
                    Success(
                        ProductDetails(
                            this.title,
                            this.description,
                            this.full_description,
                            "US $ ${this.price}",
                            this.imageUrl,
                            this.pros,
                            this.cons
                        )
                    )
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayer", exception.message, exception)
                Error(exception)
            }
        }
    }
}