package com.renarosantos.ecommerceapp.wishlist.business

/*
* Even tho the name "Repository" may lead you to think that this is part of
* the repo layer, remember that this is the "Business" definition of a Repo.
* Regardless if this data is coming from an API, SharedPreferences or Database
* this signature should remain the same.
* */
interface WishlistRepository {
    suspend fun isFavorite(productId : String) : Boolean
    suspend fun addToWishlist(productId : String)
    suspend fun removeFromWishlist(productId : String)
}