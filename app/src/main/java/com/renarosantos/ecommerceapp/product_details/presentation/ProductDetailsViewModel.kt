package com.renarosantos.ecommerceapp.product_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renarosantos.ecommerceapp.shared.business.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _viewState = MutableLiveData<ProductDetailsViewState>()
    val viewState: LiveData<ProductDetailsViewState>
        get() = _viewState

    fun loadProduct(productId: String) {
        viewModelScope.launch(dispatcher) {
            _viewState.postValue(ProductDetailsViewState.Loading)
            // Data call to fetch products
            val productDetails = repository.getProductDetails(productId)
            _viewState.postValue(ProductDetailsViewState.Content(productDetails))
        }
    }
}