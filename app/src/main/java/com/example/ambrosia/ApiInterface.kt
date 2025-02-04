package com.example.ambrosia

import com.example.ambrosia.Models.dcCat
import retrofit2.http.GET

interface ApiInterface {

    @GET("categories.php")
    suspend fun getCategory(): dcCat

}