package com.example.domain.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

class DishType(
    id: Int,
    name: String,
    imageUrl: String,
    bitmap: Bitmap?
) : BaseType(id, name, imageUrl, bitmap)