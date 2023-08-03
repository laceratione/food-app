package com.example.data.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.Nullable

open class BaseTypeDTO (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String?,
    var bitmap: Bitmap?
)