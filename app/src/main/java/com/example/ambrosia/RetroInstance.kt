package com.example.ambrosia

import com.example.ambrosia.Utils.Util
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {


    private const val BASE_URL ="https://www.themealdb.com/api/json/v1/1/"

//    val api : ApiInterface by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiInterface::class.java)
//    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiInterface by lazy {retrofit.create(ApiInterface::class.java)
    }
}