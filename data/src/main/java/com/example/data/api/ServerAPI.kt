package com.example.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServerAPI {
    private var retrofit: Retrofit? = null
    private var baseUrl: String = "https://run.mocky.io"

    fun getInstance(): Retrofit {
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}