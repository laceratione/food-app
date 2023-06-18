package com.example.data.repository

import com.example.data.api.RetrofitAPI
import com.example.domain.model.Categories
import com.example.domain.model.Dishes
import com.example.domain.repository.CloudRepository
import retrofit2.Call

class CloudRepositoryImpl(private val retrofitAPI: RetrofitAPI) : CloudRepository {
    override fun getDishTypes(): Call<Categories> {
        return retrofitAPI.getDishTypes()
    }

    override fun getCategoryDishes(): Call<Dishes> {
        return retrofitAPI.getCategoryDishes()
    }
}