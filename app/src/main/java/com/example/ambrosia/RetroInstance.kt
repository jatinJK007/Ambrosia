package com.example.ambrosia

import com.example.ambrosia.Utils.Util
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {


    private const val BASE_URL ="https://www.themealdb.com/api/json/v1/1/"
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: ApiInterface by lazy {retrofit.create(ApiInterface::class.java)
    }


    private const val Chat_Url = "https://generativelanguage.googleapis.com/"
    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
//    val geminiInstance: ApiInterface by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiInterface::class.java)
//    }
    val geminiInstance: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Chat_Url)
            .client(
                OkHttpClient.Builder()
                .addInterceptor(logger)
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}