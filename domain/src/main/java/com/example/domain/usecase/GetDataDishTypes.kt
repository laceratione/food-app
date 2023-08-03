package com.example.domain.usecase

import com.example.domain.model.DishType
import com.example.domain.repository.CloudRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetDataDishTypes(
    private val cloudRepository: CloudRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke():
            Flow<List<DishType>> = withContext(defaultDispatcher) {
        val items = cloudRepository.getDishTypes()
        items
    }
}