package com.example.foodapp.di

import com.example.data.api.RetrofitAPI
import com.example.data.api.ServerAPI
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideRetrofitAPI(): RetrofitAPI {
        return ServerAPI.getInstance().create(RetrofitAPI::class.java)
    }
}