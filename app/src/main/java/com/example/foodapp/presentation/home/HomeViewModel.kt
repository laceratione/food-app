package com.example.foodapp.presentation.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.*
import com.example.foodapp.App
import com.example.foodapp.R
import com.example.domain.usecase.GetDataDishTypes
import com.example.foodapp.DataUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel(application: Application) : ViewModel() {
    //категории блюд
    private val _dataDishTypes: MutableLiveData<List<DishType>> = MutableLiveData()
    val dataDishTypes: MutableLiveData<List<DishType>> = _dataDishTypes

    private lateinit var _selectCategory: DishType
    val selectCategory get() = _selectCategory

    private val _isOpenCategory: MutableLiveData<Boolean> = MutableLiveData()
    val isOpenCategory: LiveData<Boolean> = _isOpenCategory

    private val _isDishTypesLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isDishTypesLoading: LiveData<Boolean> = _isDishTypesLoading

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
            _isDishTypesLoading.postValue(true)
            dishTypesUseCase().enqueue(object : Callback<Categories> {
                override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                    val dishTypes = response.body()?.categories
                    viewModelScope.launch(Dispatchers.IO) {
                        dishTypes?.let {
                            DataUtils.getBitmaps(it)
                            _dataDishTypes.postValue(it)
                            _isDishTypesLoading.postValue(false)
                        }
                    }
                }

                override fun onFailure(call: Call<Categories>, t: Throwable) {
                    Log.d("myLogs", t.message.toString())
                }
            })
        }
    }

    fun loadCategoryDishes(type: DishType){
        _selectCategory = type
        _isOpenCategory.value = true
    }

}