package com.renarosantos.ecommerceapp.shared.data.repository.api

import com.renarosantos.ecommerceapp.product_list.data.ProductEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductRepositoryAPITest {

    lateinit var repository: ProductRepositoryAPI
    private val service = mockk<ProductService>()

    @Before
    fun setup() {
        repository = ProductRepositoryAPI(service)
    }

    @Test
    fun `Repository correct maps entity into Business Objects`() = runTest {
        coEvery {
            service.getProductList()
        } returns (0..2).map {
            ProductEntity(
                "id",
                "title",
                "description $it",
                6.0,
                ""
            )
        }
        val products = repository.getProductList()

        assert(products.size == 3)
        assert(products[1].description == "description 1")
    }
}
