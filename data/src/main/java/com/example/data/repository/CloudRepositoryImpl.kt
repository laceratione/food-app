package com.example.data.repository

import com.example.data.api.RetrofitAPI
import com.example.data.model.mapToDomain
import com.example.domain.model.Dish
import com.example.domain.model.DishType
import com.example.domain.repository.CloudRepository
import kotlinx.coroutines.flow.*

class CloudRepositoryImpl(private val retrofitAPI: RetrofitAPI) : CloudRepository {
    override fun getDishTypes(): Flow<List<DishType>> = flow {
        emit(retrofitAPI.getDishTypes().mapToDomain())
    }

    override fun getCategoryDishes(): Flow<List<Dish>> = flow {
        emit(retrofitAPI.getCategoryDishes().mapToDomain())
    }

}