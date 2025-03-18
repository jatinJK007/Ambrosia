package com.example.ambrosia.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_dishes")
data class SavedDish(
    @PrimaryKey val dishId: String,
    val title: String,
    val thumbnailUrl: String
)
