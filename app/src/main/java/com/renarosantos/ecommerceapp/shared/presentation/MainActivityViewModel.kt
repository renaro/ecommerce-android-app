package com.renarosantos.ecommerceapp.shared.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renarosantos.ecommerceapp.cart.business.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val cartRepository: CartRepository) :
    ViewModel() {

    fun startObservingCart() {
        viewModelScope.launch {
            cartRepository.observeChanges().collect {
                _viewState.postValue(MainActivityViewState(it.size, it.isNotEmpty()))
            }
        }
    }

    private val _viewState = MutableLiveData<MainActivityViewState>()
    val viewState: LiveData<MainActivityViewState>
        get() = _viewState

}