package com.renarosantos.ecommerceapp.shared.data.repository.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        fun getService(): ProductService {
            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val build = Retrofit.Builder()
                .client(client)
                .baseUrl("https://us-central1-android-course-api.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return build.create(ProductService::class.java)
        }
    }
}