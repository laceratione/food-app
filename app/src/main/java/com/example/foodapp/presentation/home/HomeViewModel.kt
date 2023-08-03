package com.example.foodapp.presentation.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DishType
import com.example.foodapp.App
import com.example.domain.usecase.GetDataDishTypes
import com.example.foodapp.DataUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel(application: Application) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState.Success(emptyList()))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    @Inject
    lateinit var dishTypesUseCase: GetDataDishTypes

    init {
        (application as App).appComponent.inject(this)

        viewModelScope.launch(Dispatchers.IO) {
            getDataDishTypes()
        }
    }

    //загрузка категорий блюд домашней страницы
    suspend fun getDataDishTypes() = coroutineScope {
        _uiState.value = HomeUiState.Loading()
        launch {
            dishTypesUseCase()
                .flowOn(Dispatchers.IO)
                .catch { error ->
                    _uiState.value = HomeUiState.Error(error)
                    Log.d("myLogs", error.message.toString())
                }
                .collect { types ->
                    launch {
                        DataUtils.getBitmaps(types)
                        _uiState.value = HomeUiState.Success(types)
                    }
                }
        }
    }
}

sealed class HomeUiState {
    data class Success(val types: List<DishType>) : HomeUiState()
    data class Error(val exception: Throwable) : HomeUiState()
    class Loading : HomeUiState()
}