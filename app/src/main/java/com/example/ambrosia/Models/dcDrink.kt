package com.example.ambrosia.Models

import com.google.gson.annotations.SerializedName

data class dcDrink(
    @SerializedName("drinks")
    val drinks: List<Drink>
)