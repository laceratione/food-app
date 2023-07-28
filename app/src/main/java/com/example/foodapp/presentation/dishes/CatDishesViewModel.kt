package com.example.foodapp.presentation.dishes

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Dish
import com.example.domain.model.Dishes
import com.example.domain.usecase.GetDataCategoryDishes
import com.example.foodapp.App
import com.example.foodapp.DataUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
            categoryDishesUseCase().enqueue(object : Callback<Dishes> {
                override fun onResponse(call: Call<Dishes>, response: Response<Dishes>) {
                    val dishes = response.body()?.dishes
                    viewModelScope.launch(Dispatchers.IO) {
                        dishes?.let {
                            DataUtils.getBitmaps(it)
                            _dataCategoryDishes.value = it
                            _uiState.value = CatDishesUiState.Success(it)
                        }
                    }
                }

                override fun onFailure(call: Call<Dishes>, t: Throwable) {
                    _uiState.value = CatDishesUiState.Error(t)
                    Log.d("myLogs", t.message.toString())
                }
            })
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