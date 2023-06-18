package com.example.foodapp.presentation.home

import android.app.Application
import android.util.Log
import android.view.MenuItem
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
    val dataDishTypes: MutableLiveData<List<DishType>> = MutableLiveData()

//    val dataSelectCategory: MutableLiveData<DishType> = MutableLiveData()
    lateinit var selectCategory: DishType

    //выбранная страница навигации
    private val botNavPage: MutableLiveData<Int> = MutableLiveData()
    val botNavPageLive: LiveData<Int> = botNavPage

    //СПРОСИТЬ НАСЧЕТ ЭТОГО
    private val isLoadingDishes: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingDishesLive: LiveData<Boolean> = isLoadingDishes

    val isDishTypesLoading: MutableLiveData<Boolean> = MutableLiveData()

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
            isDishTypesLoading.postValue(true)
            dishTypesUseCase().enqueue(object : Callback<Categories> {
                override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                    val dishTypes = response.body()?.categories
                    viewModelScope.launch(Dispatchers.IO) {
                        dishTypes?.let {
                            DataUtils.getBitmaps(it)
//                            getBitmaps(it)
                            dataDishTypes.postValue(it)
                            isDishTypesLoading.postValue(false)
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
        selectCategory = type
        //открыть фрагмент CategoryDishes
        isLoadingDishes.value = true
    }

    fun bottomNavItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.page_1 -> botNavPage.value = 1
            R.id.page_2 -> botNavPage.value = 2
            R.id.page_3 -> botNavPage.value = 3
            R.id.page_4 -> botNavPage.value = 4
        }

        return true
    }


}