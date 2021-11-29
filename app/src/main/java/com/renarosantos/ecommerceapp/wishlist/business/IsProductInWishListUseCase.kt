package com.renarosantos.ecommerceapp.wishlist.business

import com.renarosantos.ecommerceapp.wishlist.data.repository.WishlistRepository
import javax.inject.Inject

class IsProductInWishListUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend fun execute(productId: String): Boolean =
        wishlistRepository.isFavorite(productId)
}