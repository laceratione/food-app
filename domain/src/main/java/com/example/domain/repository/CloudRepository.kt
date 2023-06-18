package com.example.domain.repository

import com.example.domain.model.Categories
import com.example.domain.model.Dishes
import retrofit2.Call

//интерфейс облачного хранилища
interface CloudRepository {
    fun getDishTypes(): Call<Categories>
    fun getCategoryDishes(): Call<Dishes>
}