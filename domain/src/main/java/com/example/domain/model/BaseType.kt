package com.example.domain.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

open class BaseType (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    var bitmap: Bitmap?
){
    fun foo(){}
}