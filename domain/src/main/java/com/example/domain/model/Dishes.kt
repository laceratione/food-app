package com.example.domain.model

import com.google.gson.annotations.SerializedName

data class Dishes(
    @SerializedName("dishes") val dishes: List<Dish>
)
