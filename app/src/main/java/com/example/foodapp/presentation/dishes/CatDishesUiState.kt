package com.example.foodapp.presentation.dishes

import com.example.domain.model.Dish

sealed class CatDishesUiState {
    data class Success(val types: List<Dish>) : CatDishesUiState()
    data class Error(val exception: Throwable) : CatDishesUiState()
    class Loading : CatDishesUiState()
}