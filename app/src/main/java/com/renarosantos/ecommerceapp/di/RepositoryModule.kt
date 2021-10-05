package com.renarosantos.ecommerceapp.di

import com.renarosantos.ecommerceapp.ApiClient
import com.renarosantos.ecommerceapp.ProductRepository
import com.renarosantos.ecommerceapp.ProductRepositoryAPI
import com.renarosantos.ecommerceapp.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
}