package com.renarosantos.ecommerceapp.wishlist.business

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddOrRemoveFromWishListUseCaseTest {

    private val isProductInWishListUseCase = mockk<IsProductInWishListUseCase>()
    private val wishlistRepository = mockk<WishlistRepository>(relaxed = true)
    private lateinit var useCase : AddOrRemoveFromWishListUseCase

    @Before
    fun setup(){
        useCase =
            AddOrRemoveFromWishListUseCase(
                isProductInWishListUseCase,
                wishlistRepository
            )
    }

    @Test
    fun `Product is not in Wishlist, then add method is called`() = runTest {
        coEvery {
            isProductInWishListUseCase.execute(any())
        } returns false
        useCase.execute("123")
        coVerify { wishlistRepository.addToWishlist("123") }
    }

    @Test
    fun `Product is in Wishlist, then remove method is called`() = runTest {
        coEvery {
            isProductInWishListUseCase.execute(any())
        } returns true
        useCase.execute("123")
        coVerify { wishlistRepository.removeFromWishlist("123") }
    }
}