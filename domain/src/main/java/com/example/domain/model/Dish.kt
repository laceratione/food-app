package com.example.domain.model

import android.graphics.Bitmap

class Dish(
    id: Int,
    name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    imageUrl: String?,
    val tegs: List<String>,
    bitmap: Bitmap?,
    var count: Int = 0
) : BaseType(id, name, imageUrl, bitmap)