package com.example.ambrosia.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedDishDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dish: SavedDish)

    @Delete
    suspend fun delete(dish: SavedDish)

    @Query("SELECT * FROM saved_dishes")
    fun getAllDishes(): Flow<List<SavedDish>>
}