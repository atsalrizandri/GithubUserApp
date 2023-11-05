package com.atsalaja.githubuserapp.data.retrofit

import com.atsalaja.githubuserapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(): ApiService {
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val mySuperSecretKey = BuildConfig.KEY
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", mySuperSecretKey)
                .build()
            chain.proceed(requestHeaders)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
        val BASE_URL = BuildConfig.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}