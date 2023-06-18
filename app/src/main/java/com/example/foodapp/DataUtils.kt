package com.example.foodapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.domain.model.BaseType
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.URL

class DataUtils {
    companion object{
        suspend fun getBitmaps(items: List<BaseType>) = coroutineScope {
            launch {
                for (item in items) {
                    try {
                        val picture = URL(item.imageUrl).openStream()
                        val bitmap: Bitmap = BitmapFactory.decodeStream(picture)
                        item.bitmap = bitmap
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

}