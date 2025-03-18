package com.example.ambrosia.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ambrosia.Repository.OpenAIRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = OpenAIRepository()
    val suggestions = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun searchDishes(ingredients: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = repository.getDishSuggestions(ingredients)
            suggestions.postValue(result)
            isLoading.postValue(false)
        }
    }
}