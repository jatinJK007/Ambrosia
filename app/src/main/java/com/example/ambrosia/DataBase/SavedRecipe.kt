package com.example.ambrosia.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_recipes")

data class SavedRecipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val thumbnailUrl: String
)
