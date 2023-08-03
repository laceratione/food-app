package com.example.data.model

import android.graphics.Bitmap
import com.example.domain.model.DishType

class DishTypeDTO(
    id: Int,
    name: String,
    imageUrl: String,
    bitmap: Bitmap?
) : BaseTypeDTO(id, name, imageUrl, bitmap)

fun DishTypeDTO.mapToDomain() = DishType(
    id = this.id,
    name = this.name,
    imageUrl = this.imageUrl,
    bitmap = this.bitmap
)