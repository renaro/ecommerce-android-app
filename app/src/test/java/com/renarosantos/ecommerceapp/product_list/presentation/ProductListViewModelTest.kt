package com.renarosantos.ecommerceapp.product_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.renarosantos.ecommerceapp.cart.business.CartRepository
import com.renarosantos.ecommerceapp.product_list.business.Product
import com.renarosantos.ecommerceapp.shared.business.ProductRepository
import com.renarosantos.ecommerceapp.wishlist.business.AddOrRemoveFromWishListUseCase
import com.renarosantos.ecommerceapp.wishlist.business.IsProductInWishListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ProductListViewModel
    private val repository = mockk<ProductRepository>()
    private val isProductInWishListUseCase = mockk<IsProductInWishListUseCase>()
    private val cartRepository = mockk<CartRepository>()
    private val addOrRemoveUseCase = mockk<AddOrRemoveFromWishListUseCase>()
    private val listOfProducts = (0..2).map {
        Product("title", "description", 6.0, "", "id-$it")
    }

    @Before
    fun setUp() {
        coEvery { isProductInWishListUseCase.execute(any()) } returns false
        coEvery {
            isProductInWishListUseCase.execute("id-1")
        } returns true
        coEvery {
            repository.getProductList()
        } returns listOfProducts

        viewModel = ProductListViewModel(
            repository,
            isProductInWishListUseCase,
            addOrRemoveUseCase,
            cartRepository,
            dispatcher
        )
    }

    @Test
    fun `Load method correctly creates the ViewState`() = runTest {
        val values = mutableListOf<ProductListViewState>()
        viewModel.viewState.observeForever {
            values.add(it)
        }
        viewModel.loadProductList()
        dispatcher.scheduler.advanceUntilIdle()

        assert(values[0] is ProductListViewState.Loading)
        assert(values[1] ==
                ProductListViewState.Content(
                    (0..2).map {
                        ProductCardViewState(
                            "id-$it",
                            "title",
                            "description",
                            "US $ 6.0",
                            "",
                            it == 1,
                            false
                        )
                    }
                ))
    }

    @Test
    fun `Check if ViewState is correct for items in the cart`() = runTest {
        val values = mutableListOf<ProductListViewState>()
        viewModel.viewState.observeForever {
            values.add(it)
        }
        coEvery { cartRepository.observeChanges() } returns flowOf(listOf("id-2"))
        viewModel.loadProductList()
        dispatcher.scheduler.advanceUntilIdle()

        assert(values[0] is ProductListViewState.Loading)
        assert(values[1] ==
                ProductListViewState.Content(
                    (0..2).map {
                        ProductCardViewState(
                            "id-$it",
                            "title",
                            "description",
                            "US $ 6.0",
                            "",
                            it == 1,
                            it == 2
                        )
                    }
                ))

    }

}