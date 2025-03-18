package com.example.ambrosia.Repository

import com.example.ambrosia.Room.SavedDish
import com.example.ambrosia.Room.SavedDishDao
import kotlinx.coroutines.flow.Flow

class SavedDishRepository(private val savedDishDao: SavedDishDao) {
    val allDishes:Flow<List<SavedDish>> =savedDishDao.getAllDishes()

    suspend fun insert(dish: SavedDish) {
        savedDishDao.insert(dish)
    }

    suspend fun delete(dish: SavedDish) {
        savedDishDao.delete(dish)
    }
}