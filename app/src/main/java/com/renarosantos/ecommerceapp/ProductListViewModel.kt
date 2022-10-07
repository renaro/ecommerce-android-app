package com.renarosantos.ecommerceapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductListViewModel : ViewModel() {

    private val _viewState = MutableLiveData<ProductListViewState>()
    val viewState: LiveData<ProductListViewState>
        get() = _viewState

    fun loadProducts() {
        _viewState.postValue(ProductListViewState.Loading)
        _viewState.postValue(ProductListViewState.Content(
            (1..3).map {
                ProductCardViewState(
                    "Product $it",
                    "Description of product",
                    "100 US$", ""
                )
            }
        ))
    }
}