package com.renarosantos.ecommerceapp.product_details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.renarosantos.ecommerceapp.shared.business.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsViewModelTest {

    private val repository = mockk<ProductRepository>(relaxed = true)
    private lateinit var viewModel: ProductDetailsViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        viewModel = ProductDetailsViewModel(repository, dispatcher)
    }

    @Test
    fun `Test loading state is shown before the content`() {
        val viewStates = mutableListOf<ProductDetailsViewState>()
        viewModel.viewState.observeForever {
            viewStates.add(it)
        }
        coEvery { repository.getProductDetails("anyId") } returns mockk()
        viewModel.loadProduct("anyId")
        dispatcher.scheduler.advanceUntilIdle()
        assert(viewStates[0] is ProductDetailsViewState.Loading)
        assert(viewStates[1] is ProductDetailsViewState.Content)
    }
}