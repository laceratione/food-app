package com.example.data.model

import com.example.domain.model.DishType
import com.google.gson.annotations.SerializedName

data class CategoriesDTO(
    @SerializedName("сategories") val categories: List<DishTypeDTO>
)

fun CategoriesDTO.mapToDomain(): List<DishType> = categories.map { it.mapToDomain() }