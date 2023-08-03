package com.example.data.model

import com.example.domain.model.Dish
import com.google.gson.annotations.SerializedName

data class DishesDTO(
    @SerializedName("dishes") val dishes: List<DishDTO>
)

fun DishesDTO.mapToDomain(): List<Dish> = dishes.map { it.mapToDomain() }
