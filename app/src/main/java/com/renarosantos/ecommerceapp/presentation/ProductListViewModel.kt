package com.renarosantos.ecommerceapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renarosantos.ecommerceapp.data.repository.ProductRepository
import com.renarosantos.ecommerceapp.presentation.viewstate.ProductCardViewState
import com.renarosantos.ecommerceapp.presentation.viewstate.ProductListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _viewState = MutableLiveData<ProductListViewState>()
    val viewState: LiveData<ProductListViewState>
        get() = _viewState


    fun loadProductList() {
        viewModelScope.launch {
            _viewState.postValue(ProductListViewState.Loading)
            // Data call to fetch products
            val productList = repository.getProductList()
            _viewState.postValue(ProductListViewState.Content(
                productList.map {
                    ProductCardViewState(
                        it.title,
                        it.description,
                        "US $ ${it.price}",
                        it.imageUrl
                    )
                }
            ))
        }
    }
}