package com.example.domain.usecase

import com.example.domain.model.Dish
import com.example.domain.repository.CloudRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetDataCategoryDishes(
    private val cloudRepository: CloudRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke():
            Flow<List<Dish>> = withContext(defaultDispatcher) {
        val items = cloudRepository.getCategoryDishes()
        items
    }
}