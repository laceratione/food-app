package com.example.foodapp.presentation.home

import com.example.domain.model.DishType

sealed class HomeUiState {
    data class Success(val types: List<DishType>) : HomeUiState()
    data class Error(val exception: Throwable) : HomeUiState()
    class Loading : HomeUiState()
}
