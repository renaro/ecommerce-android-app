package com.renarosantos.ecommerceapp.wishlist.data.repository

import com.renarosantos.ecommerceapp.shared.data.repository.database.FavoriteProductEntity
import com.renarosantos.ecommerceapp.shared.data.repository.database.WishlistDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WishlistDatabaseRepository(val wishlistDAO: WishlistDAO) : WishlistRepository {

    override suspend fun isFavorite(productId: String): Boolean {
        return withContext(Dispatchers.IO) {
            wishlistDAO.getFavoriteProducts(productId).isNotEmpty()
        }
    }

    override suspend fun addToWishlist(productId: String, title: String) {
        return withContext(Dispatchers.IO) {
            wishlistDAO.insertAll(FavoriteProductEntity(productId, title))
        }
    }

    override suspend fun removeFromWishlist(productId: String) {
        return withContext(Dispatchers.IO) {
            wishlistDAO.delete(FavoriteProductEntity(productId, ""))
        }
    }
}