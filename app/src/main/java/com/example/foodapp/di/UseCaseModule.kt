package com.example.foodapp.di

import com.example.domain.repository.CloudRepository
import com.example.domain.usecase.GetDataCategoryDishes
import com.example.domain.usecase.GetDataDishTypes
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideGetDataDishTypes(cloudRepository: CloudRepository): GetDataDishTypes {
        return GetDataDishTypes(cloudRepository)
    }

    @Provides
    fun provideGetDataCategoryDishes(cloudRepository: CloudRepository): GetDataCategoryDishes {
        return GetDataCategoryDishes(cloudRepository)
    }
}