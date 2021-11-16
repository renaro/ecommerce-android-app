package com.renarosantos.ecommerceapp.wishlist.data.repository

interface WishlistRepository {
    fun isFavorite(productId : String) : Boolean
    fun addToWishlist(productId : String)
    fun removeFromWishlist(productId : String)
}