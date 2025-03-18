package com.example.ambrosia.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Drink(
    @SerializedName("idDrink")
    val idDrink: String,
    @SerializedName("strDrink")
    val strDrink: String,
    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String
): Serializable