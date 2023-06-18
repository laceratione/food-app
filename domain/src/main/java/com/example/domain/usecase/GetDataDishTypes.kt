package com.example.domain.usecase

import com.example.domain.model.Categories
import com.example.domain.repository.CloudRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class GetDataDishTypes(
    private val cloudRepository: CloudRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
){
    suspend operator fun invoke():
            Call<Categories> = withContext(defaultDispatcher) {
        val items = cloudRepository.getDishTypes()
        items
    }
}