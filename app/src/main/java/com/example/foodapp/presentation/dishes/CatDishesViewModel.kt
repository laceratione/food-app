package com.example.foodapp.presentation.dishes

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Dish
import com.example.domain.usecase.GetDataCategoryDishes
import com.example.foodapp.App
import com.example.foodapp.DataUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatDishesViewModel(application: Application) : ViewModel() {
    //блюда конкретной категории
    private val _dataCategoryDishes: MutableStateFlow<List<Dish>> =
        MutableStateFlow(value = emptyList())

    private val _uiState: MutableStateFlow<CatDishesUiState> =
        MutableStateFlow(CatDishesUiState.Success(emptyList()))
    val uiState: StateFlow<CatDishesUiState> = _uiState.asStateFlow()

    //событие "Назад"
    private val _isBackPressed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isBackPressed: StateFlow<Boolean> = _isBackPressed.asStateFlow()

    @Inject
    lateinit var categoryDishesUseCase: GetDataCategoryDishes

    init {
        (application as App).appComponent.inject(this)

        viewModelScope.launch(Dispatchers.IO) {
            getDataCategoryDishes()
        }
    }

    //загрузка блюд выбранной категории
    suspend fun getDataCategoryDishes() = coroutineScope {
        _uiState.value = CatDishesUiState.Loading()
        launch {
            categoryDishesUseCase()
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    _uiState.value = CatDishesUiState.Error(error)
                    Log.d("myLogs", error.message.toString())
                }
                .collect { dishes ->
                    launch {
                        DataUtils.getBitmaps(dishes)
                        _dataCategoryDishes.value = dishes
                        _uiState.value = CatDishesUiState.Success(dishes)
                    }
                }
        }
    }

    //фильтрация блюд по тегам
    fun filterDishes(tags: List<String>): List<Dish>? {
        if (tags.size == 0)
            return null
        else
            return _dataCategoryDishes.value.filter { dish -> dish.tegs.containsAll(tags) }
    }

    fun back() {
        _isBackPressed.value = true
    }
}

sealed class CatDishesUiState {
    data class Success(val types: List<Dish>) : CatDishesUiState()
    data class Error(val exception: Throwable) : CatDishesUiState()
    class Loading : CatDishesUiState()
}