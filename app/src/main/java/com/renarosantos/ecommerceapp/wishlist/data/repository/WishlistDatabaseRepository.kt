package com.renarosantos.ecommerceapp.wishlist.data.repository

import javax.inject.Inject

class WishlistDatabaseRepository @Inject constructor() : WishlistRepository {

    override fun isFavorite(productId: String): Boolean {
        return false
    }

    override fun addToWishlist(productId: String) {
    }

    override fun removeFromWishlist(productId: String) {
    }
}