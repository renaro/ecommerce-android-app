package com.renarosantos.ecommerceapp.product_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renarosantos.ecommerceapp.cart.business.CartRepository
import com.renarosantos.ecommerceapp.shared.business.ProductRepository
import com.renarosantos.ecommerceapp.shared.business.Result
import com.renarosantos.ecommerceapp.shared.presentation.PriceFormatter
import com.renarosantos.ecommerceapp.shared.presentation.SingleLiveEvent
import com.renarosantos.ecommerceapp.wishlist.business.AddOrRemoveFromWishListUseCase
import com.renarosantos.ecommerceapp.wishlist.business.IsProductInWishListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val isProductInWishListUseCase: IsProductInWishListUseCase,
    private val addOrRemoveFromWishListUseCase: AddOrRemoveFromWishListUseCase,
    private val cartRepository: CartRepository,
    private val priceFormatter: PriceFormatter,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _viewState = MutableStateFlow<ProductListViewState>(ProductListViewState.Loading)
    val viewState = _viewState.asStateFlow()

    // Can be used for navigation/snackbar/toast/etc...
    val cartEvents = SingleLiveEvent<AddToCartEvent>()

    init {
        viewModelScope.launch(dispatcher) {
            cartRepository.observeChanges().collect {
                (_viewState.value as? ProductListViewState.Content)?.let { content ->
                    _viewState.emit(
                        ProductListViewState.Content(
                            content.productList.map { item ->
                                item.copy(isProductInCart = it.contains(item.id))
                            }
                        )
                    )
                }
            }
        }
    }

    fun loadProductList() {
        viewModelScope.launch(dispatcher) {
            _viewState.emit(ProductListViewState.Loading)
            // Data call to fetch products
            val result = repository.getProductList()
            val productsInCart = cartRepository.observeChanges().first()
            when (result) {
                is Result.Error -> _viewState.emit(ProductListViewState.Error)
                is Result.Success -> _viewState.emit(
                    ProductListViewState.Content(
                        result.data.map {
                            ProductCardViewState(
                                it.productId,
                                it.title,
                                it.description,
                                priceFormatter.format(it.price),
                                it.imageUrl,
                                isProductInWishListUseCase.execute(it.productId),
                                productsInCart.contains(it.productId)
                            )
                        }
                    ))
            }
        }
    }

    fun favoriteIconClicked(productId: String) {
        viewModelScope.launch(dispatcher) {
            addOrRemoveFromWishListUseCase.execute(productId)
            val currentViewState = _viewState.value
            (currentViewState as? ProductListViewState.Content)?.let { content ->
                _viewState.emit(
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
            if (cartRepository.observeChanges().first().contains(id)) {
                cartEvents.postValue(AddToCartEvent(false))
            } else {
                cartRepository.addToCart(id)
                cartEvents.postValue(AddToCartEvent(true))
            }
        }
    }

    fun removeClicked(id: String) {
        viewModelScope.launch(dispatcher) {
            cartRepository.removeFromCart(id)
        }
    }

    fun onCartClicked(id: String) {
        viewModelScope.launch(dispatcher) {
            if (cartRepository.observeChanges().first().contains(id)) {
                println("Removing from cart")
                cartRepository.removeFromCart(id)
            } else {
                println("Adding to Cart from cart")
                cartRepository.addToCart(id)
                cartEvents.postValue(AddToCartEvent(true))
            }
        }
    }

    data class AddToCartEvent(val isSuccess: Boolean)
}
