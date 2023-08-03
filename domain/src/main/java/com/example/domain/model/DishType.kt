package com.example.domain.model

import android.graphics.Bitmap

class DishType(
    id: Int,
    name: String,
    imageUrl: String?,
    bitmap: Bitmap?
) : BaseType(id, name, imageUrl, bitmap)
