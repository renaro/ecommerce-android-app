package com.renarosantos.ecommerceapp.di

import com.renarosantos.ecommerceapp.data.repository.api.ApiClient
import com.renarosantos.ecommerceapp.data.repository.ProductRepository
import com.renarosantos.ecommerceapp.data.repository.api.ProductRepositoryAPI
import com.renarosantos.ecommerceapp.data.repository.api.ProductService
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