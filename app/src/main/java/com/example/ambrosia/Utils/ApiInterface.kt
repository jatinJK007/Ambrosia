package com.example.ambrosia.Utils

import com.example.ambrosia.Models.GeminiRequest
import com.example.ambrosia.Models.GeminiResponse
import com.example.ambrosia.Models.dcCat
import com.example.ambrosia.Models.dcDrink
import com.example.ambrosia.Models.dcRecomm
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @GET("categories.php")
    suspend fun getCategory(): dcCat

    @GET("filter.php?a=Alcoholic")
    suspend fun getDrink():dcDrink

    @GET("filter.php?a=Indian")
    suspend fun getRecomm():dcRecomm


    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    suspend fun generateContent(
        @retrofit2.http.Query("key") apiKey :String,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>

}