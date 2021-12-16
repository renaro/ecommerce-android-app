package com.renarosantos.ecommerceapp.product_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renarosantos.ecommerceapp.cart.business.CartRepository
import com.renarosantos.ecommerceapp.shared.business.ProductRepository
import com.renarosantos.ecommerceapp.shared.presentation.SingleLiveEvent
import com.renarosantos.ecommerceapp.wishlist.business.AddOrRemoveFromWishListUseCase
import com.renarosantos.ecommerceapp.wishlist.business.IsProductInWishListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val isProductInWishListUseCase: IsProductInWishListUseCase,
    private val addOrRemoveFromWishListUseCase: AddOrRemoveFromWishListUseCase,
    private val cartRepository: CartRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _viewState = MutableLiveData<ProductListViewState>()
    val viewState: LiveData<ProductListViewState>
        get() = _viewState

    // Can be used for navigation/snackbar/toast/etc...
    val singleEvents = SingleLiveEvent<AddToCartEvent>()


    fun loadProductList() {
        viewModelScope.launch(dispatcher) {
            _viewState.postValue(ProductListViewState.Loading)
            // Data call to fetch products
            val productList = repository.getProductList()
            _viewState.postValue(
                ProductListViewState.Content(
                    productList.map {
                        ProductCardViewState(
                            it.productId,
                            it.title,
                            it.description,
                            "US $ ${it.price}",
                            it.imageUrl,
                            isProductInWishListUseCase.execute(it.productId)
                        )
                    }
                ))
        }
    }

    fun favoriteIconClicked(productId: String) {
        viewModelScope.launch(dispatcher) {
            addOrRemoveFromWishListUseCase.execute(productId)
            val currentViewState = _viewState.value
            (currentViewState as? ProductListViewState.Content)?.let { content ->
                _viewState.postValue(
                    content.updateFavoriteProduct(
                        productId,
                        isProductInWishListUseCase.execute(productId)
                    )
                )
            }
        }
    }

    fun onBuyClicked(id: String) {
        viewModelScope.launch(dispatcher) {
            val addToCartResult = cartRepository.addToCart(id)
            singleEvents.postValue(AddToCartEvent(addToCartResult is CartRepository.AddToCartResult.Success))
        }
    }

    data class AddToCartEvent(val isSuccess: Boolean)
}
