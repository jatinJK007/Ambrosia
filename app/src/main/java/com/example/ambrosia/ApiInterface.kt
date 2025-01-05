package com.example.ambrosia

import com.example.ambrosia.Models.Category
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("1/categories.php")
    fun getCategory():Response<Category>

}