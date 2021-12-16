package com.renarosantos.ecommerceapp.shared.business

import com.renarosantos.ecommerceapp.product_list.business.Product
import com.renarosantos.ecommerceapp.product_details.business.ProductDetails

/*
* Even tho the name "Repository" may lead you to think that this is part of
* the repo layer, remember that this is the "Business" definition of a Repo.
* Regardless if this data is coming from an API, SharedPreferences or Database
* this signature should remain the same.
* */
interface ProductRepository {

    suspend fun getProductList(): List<Product>

    suspend fun getProductDetails(productId: String): ProductDetails
}