package com.noemi.carsreloded.network

import com.noemi.carsreloded.model.remote.RemoteCar
import com.noemi.carsreloded.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CarServiceAPI {

    @GET("availablecars")
    suspend fun getCars(): Response<List<RemoteCar>>

    companion object {

        fun initCarService(): CarServiceAPI {
            val interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(CarServiceAPI::class.java)
        }
    }
}