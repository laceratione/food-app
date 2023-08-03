package com.example.domain.model

import android.graphics.Bitmap

open class BaseType(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    var bitmap: Bitmap?
)