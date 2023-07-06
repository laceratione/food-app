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
    //категории блюд
    private val _dataDishTypes: MutableStateFlow<List<DishType>> =
        MutableStateFlow(value = emptyList())
    val dataDishTypes: StateFlow<List<DishType>> = _dataDishTypes.asStateFlow()

    //процесс загрузки
    private val _isDishTypesLoading: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val isDishTypesLoading: StateFlow<Boolean> = _isDishTypesLoading.asStateFlow()

    @Inject
    lateinit var dishTypesUseCase: GetDataDishTypes

    init {
        (application as App).appComponent.inject(this)

        viewModelScope.launch(Dispatchers.IO) {
            getDataDishTypes()
        }.start()
    }

    //загрузка категорий блюд домашней страницы
    suspend fun getDataDishTypes() = coroutineScope {
        launch {
            _isDishTypesLoading.value = true
            dishTypesUseCase().enqueue(object : Callback<Categories> {
                override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                    val dishTypes = response.body()?.categories
                    viewModelScope.launch(Dispatchers.IO) {
                        dishTypes?.let {
                            DataUtils.getBitmaps(it)
                            _dataDishTypes.value = it
                            _isDishTypesLoading.value = false
                        }
                    }
                }

                override fun onFailure(call: Call<Categories>, t: Throwable) {
                    Log.d("myLogs", t.message.toString())
                }
            })
        }
    }

}