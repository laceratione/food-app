package com.example.foodapp.di

import com.example.data.api.RetrofitAPI
import com.example.data.repository.CloudRepositoryImpl
import com.example.domain.repository.CloudRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule{

    @Singleton
    @Provides
    fun provideCloudRepositoryImpl(retrofitAPI: RetrofitAPI): CloudRepository {
        return CloudRepositoryImpl(retrofitAPI)
    }
}