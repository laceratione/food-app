package com.example.foodapp.di

import com.example.data.api.RetrofitAPI
import com.example.data.repository.CloudRepositoryImpl
import com.example.domain.repository.CloudRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule{

    @Provides
    fun provideCloudRepositoryImpl(retrofitAPI: RetrofitAPI): CloudRepository {
        return CloudRepositoryImpl(retrofitAPI)
    }
}