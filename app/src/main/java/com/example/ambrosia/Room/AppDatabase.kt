package com.example.ambrosia.Room

import androidx.room.Database


@Database(entities = [SavedDish::class], version = 1)
abstract class AppDatabase {
    abstract fun savedDishDao(): SavedDishDao
}