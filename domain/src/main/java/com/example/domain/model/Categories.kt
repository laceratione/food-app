package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("сategories") val categories: List<DishType>
)

