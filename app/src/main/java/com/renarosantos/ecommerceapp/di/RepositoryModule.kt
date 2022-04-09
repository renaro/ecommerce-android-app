package com.renarosantos.ecommerceapp.di

import android.content.Context
import androidx.room.Room
import com.renarosantos.ecommerceapp.cart.business.CartRepository
import com.renarosantos.ecommerceapp.cart.data.CartRepositorySharedPreferences
import com.renarosantos.ecommerceapp.shared.business.ProductRepository
import com.renarosantos.ecommerceapp.shared.data.repository.api.ApiClient
import com.renarosantos.ecommerceapp.shared.data.repository.api.ProductRepositoryAPI
import com.renarosantos.ecommerceapp.shared.data.repository.api.ProductService
import com.renarosantos.ecommerceapp.shared.presentation.InternationalPriceFormatter
import com.renarosantos.ecommerceapp.shared.presentation.PriceFormatter
import com.renarosantos.ecommerceapp.wishlist.business.WishlistRepository
import com.renarosantos.ecommerceapp.wishlist.data.repository.WishlistDatabaseRepository
import com.renarosantos.ecommerceapp.wishlist.data.repository.database.AppDatabase
import com.renarosantos.ecommerceapp.wishlist.data.repository.database.WishListDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesProductService(): ProductService = ApiClient.getService()

    @Provides
    fun providedsProductRepositoryAPI(
        service: ProductService
    ): ProductRepositoryAPI = ProductRepositoryAPI(service)

    @Provides
    fun providesProductRepository(
        productRepositoryAPI: ProductRepositoryAPI
    ): ProductRepository = productRepositoryAPI

    @Provides
    fun providesWishlistRepository(
        databaseRepository: WishlistDatabaseRepository
    ): WishlistRepository = databaseRepository

    @Provides
    fun providesWishlistDatabaseRepository(databaseDAO: WishListDAO): WishlistDatabaseRepository {
        return WishlistDatabaseRepository(databaseDAO)
    }

    @Provides
    fun providesWishListDAO(
        @ApplicationContext context: Context
    ): WishListDAO {
        val db = Room.databaseBuilder(
            context, AppDatabase::class.java,
            "ecommerce-database"
        ).build()
        return db.wishListDao()
    }

    @Provides
    @Singleton
    fun providesCartRepository(@ApplicationContext context: Context): CartRepository {
        return CartRepositorySharedPreferences(context)
    }

    @Provides
    @Singleton
    fun providesPriceFormatter(): PriceFormatter {
        return InternationalPriceFormatter
    }

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}