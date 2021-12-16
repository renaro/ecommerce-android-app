package com.renarosantos.ecommerceapp.shared.presentation

/* For now only this is required to the
ViewState of my MainActivity, therefore I don't need a sealed class
 */

data class MainActivityViewState(
    val productsInCartCount: Int = 0,
    val isBadgeVisible: Boolean = false
)
