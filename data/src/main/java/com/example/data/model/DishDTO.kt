package com.example.data.model

import android.graphics.Bitmap
import com.example.domain.model.Dish
import com.google.gson.annotations.SerializedName

class DishDTO(
    id: Int,
    name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("description") val description: String,
    imageUrl: String?,
    @SerializedName("tegs") val tegs: List<String>,
    bitmap: Bitmap?,
    var count: Int = 0
) : BaseTypeDTO(id, name, imageUrl, bitmap)

fun DishDTO.mapToDomain() = Dish(
    id = this.id,
    name = this.name,
    price = this.price,
    weight = this.weight,
    description = this.description,
    imageUrl = this.imageUrl,
    tegs = this.tegs,
    bitmap = this.bitmap,
    count = this.count
)
