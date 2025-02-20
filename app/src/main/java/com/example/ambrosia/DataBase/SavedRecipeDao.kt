package com.example.ambrosia.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SavedRecipeDao {
    @Insert
    suspend fun saveRecipe(recipe: SavedRecipe)

    @Query("SELECT * FROM saved_recipes")
    suspend fun getAllSavedRecipes(): List<SavedRecipe>
}