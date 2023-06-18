package com.example.domain.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

class Dish(
    id: Int,
    name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("description") val description: String,
    imageUrl: String,
    @SerializedName("tegs") val tegs: List<String>,
    bitmap: Bitmap?,
    var count: Int = 0
) : BaseType(id, name, imageUrl, bitmap)
