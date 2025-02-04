package com.example.ambrosia.Models

import com.google.gson.annotations.SerializedName

data class dcCat(
    @SerializedName("categories")
    val categories: List<Category>
)