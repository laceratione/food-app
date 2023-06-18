package com.example.data.api

import com.example.domain.model.Categories
import com.example.domain.model.Dishes
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {
    //возвращает категории блюд
    @GET("/v3/058729bd-1402-4578-88de-265481fd7d54")
    fun getDishTypes(): Call<Categories>

    //возвращает блюда выбранной категории
    @GET("/v3/c7a508f2-a904-498a-8539-09d96785446e")
    fun getCategoryDishes(): Call<Dishes>
}