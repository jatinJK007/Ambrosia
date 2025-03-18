package com.example.ambrosia.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ambrosia.Repository.SavedDishRepository
import com.example.ambrosia.Room.AppDatabase
import com.example.ambrosia.Room.SavedDish

class SavedDishViewModel(application: Application) : AndroidViewModel(application)  {
    private val repository: SavedDishRepository
    val allDishes: LiveData<List<SavedDish>>
    init {
        val dao = AppDatabase.getDatabase(application).savedDishDao()
        repository = SavedDishRepository(dao)
        allDishes = repository.allDishes.asLiveData()
    }

    fun insert(dish: SavedDish) = viewModelScope.launch {
        repository.insert(dish)
    }
}