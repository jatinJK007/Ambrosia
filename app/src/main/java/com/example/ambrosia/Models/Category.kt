package com.example.ambrosia.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(
    @SerializedName("idCategory")
    val idCategory: String,
    @SerializedName("strCategory")
    val strCategory: String,
    @SerializedName("strCategoryThumb")
    val strCategoryThumb: String,
    @SerializedName("strCategoryDescription")
    val strCategoryDescription: String
): Serializable