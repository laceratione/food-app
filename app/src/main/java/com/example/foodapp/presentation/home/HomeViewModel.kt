package com.example.foodapp.presentation.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.*
import com.example.foodapp.App
import com.example.domain.usecase.GetDataDishTypes
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
            dishTypesUseCase().enqueue(object : Callback<Categories> {
                override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                    val dishTypes = response.body()?.categories
                    viewModelScope.launch(Dispatchers.IO) {
                        dishTypes?.let {
                            DataUtils.getBitmaps(it)
                            _uiState.value = HomeUiState.Success(it)
                        }
                    }
                }

                override fun onFailure(call: Call<Categories>, t: Throwable) {
                    _uiState.value = HomeUiState.Error(t)
                    Log.d("myLogs", t.message.toString())
                }
            })
        }
    }

}

sealed class HomeUiState {
    data class Success(val types: List<DishType>) : HomeUiState()
    data class Error(val exception: Throwable) : HomeUiState()
    class Loading : HomeUiState()
}