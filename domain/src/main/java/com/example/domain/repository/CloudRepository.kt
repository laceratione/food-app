package com.example.domain.repository

import com.example.domain.model.DishType
import com.example.domain.model.Dish
import kotlinx.coroutines.flow.Flow

interface CloudRepository {
    fun getDishTypes(): Flow<List<DishType>>

    fun getCategoryDishes(): Flow<List<Dish>>
}