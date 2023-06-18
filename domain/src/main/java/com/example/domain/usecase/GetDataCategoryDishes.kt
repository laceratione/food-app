package com.example.domain.usecase

import com.example.domain.model.Dishes
import com.example.domain.repository.CloudRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class GetDataCategoryDishes(
    private val cloudRepository: CloudRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke():
            Call<Dishes> = withContext(defaultDispatcher) {
        val items = cloudRepository.getCategoryDishes()
        items
    }
}