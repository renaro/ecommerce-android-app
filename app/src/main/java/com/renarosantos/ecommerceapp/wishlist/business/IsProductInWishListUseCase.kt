package com.renarosantos.ecommerceapp.wishlist.business

import javax.inject.Inject

class IsProductInWishListUseCase @Inject constructor(
    private val wishlistRepository: WishlistRepository
) {
    suspend fun execute(productId: String): Boolean =
        wishlistRepository.isFavorite(productId)
}