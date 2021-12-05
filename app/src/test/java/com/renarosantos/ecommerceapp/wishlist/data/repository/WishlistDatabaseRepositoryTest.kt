package com.renarosantos.ecommerceapp.wishlist.data.repository

import com.renarosantos.ecommerceapp.wishlist.data.repository.database.FavoriteProductEntity
import com.renarosantos.ecommerceapp.wishlist.data.repository.database.WishListDAO
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WishlistDatabaseRepositoryTest {

    private lateinit var repository: WishlistDatabaseRepository
    private val dao = mockk<WishListDAO>()

    @Before
    fun setup() {
        repository = WishlistDatabaseRepository(dao)
    }

    @Test
    fun `When given product is saved in Database, then repository returns true`() = runTest {
        coEvery {
            dao.isProductFavorite("1")
        } returns FavoriteProductEntity("1", "name")
        assert(repository.isFavorite("1"))
    }

    @Test
    fun `When given product is not saved in Database, then repository returns false`() = runTest {
        coEvery {
            dao.isProductFavorite("1")
        } returns null
        assert(!repository.isFavorite("1"))
    }
}