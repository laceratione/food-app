package com.example.foodapp.presentation.dishes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CatDishesModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CatDishesViewModel::class.java)){
            return CatDishesViewModel(application) as T
        }
        throw IllegalArgumentException("Class not found")
    }
}