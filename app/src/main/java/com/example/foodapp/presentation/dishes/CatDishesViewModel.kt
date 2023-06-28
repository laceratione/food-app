package com.example.foodapp.presentation.dishes

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Dish
import com.example.domain.model.Dishes
import com.example.domain.usecase.GetDataCategoryDishes
import com.example.foodapp.App
import com.example.foodapp.DataUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CatDishesViewModel(application: Application) : ViewModel() {
    //блюда конкретной категории
    private val _dataCategoryDishes: MutableLiveData<List<Dish>> = MutableLiveData()
    val dataCategoryDishes: MutableLiveData<List<Dish>> = _dataCategoryDishes

    //процесс загрузки
    private val _isCategoryDishesLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isCategoryDishesLoading: MutableLiveData<Boolean> = _isCategoryDishesLoading

    //событие "Назад"
    val isBackPressed: MutableLiveData<Boolean> = MutableLiveData()

    @Inject
    lateinit var categoryDishesUseCase: GetDataCategoryDishes

    init {
        (application as App).appComponent.inject(this)

        viewModelScope.launch(Dispatchers.IO) {
            getDataCategoryDishes()
        }.start()
    }

    //загрузка блюд выбранной категории
    suspend fun getDataCategoryDishes() = coroutineScope {
        launch {
            _isCategoryDishesLoading.postValue(true)
            categoryDishesUseCase().enqueue(object : Callback<Dishes> {
                override fun onResponse(call: Call<Dishes>, response: Response<Dishes>) {
                    val dishes = response.body()?.dishes
                    viewModelScope.launch(Dispatchers.IO) {
                        dishes?.let {
                            DataUtils.getBitmaps(it)
//                            getBitmaps(it)
                            _dataCategoryDishes.postValue(it)
                            _isCategoryDishesLoading.postValue(false)
                        }
                    }
                }

                override fun onFailure(call: Call<Dishes>, t: Throwable) {
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
            return _dataCategoryDishes.value?.filter { dish -> dish.tegs.containsAll(tags) }
    }

    fun back() {
        isBackPressed.postValue(true)
    }
}