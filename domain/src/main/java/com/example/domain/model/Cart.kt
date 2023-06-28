package com.example.domain.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//корзина покупок
object Cart {
    private var dishes: MutableList<Dish> = mutableListOf()

    private var _summ: Int = 0
    private val _summLive: MutableLiveData<Int> = MutableLiveData(0)
    val summLive: LiveData<Int> = _summLive

    fun setSumm() {
        _summLive.postValue(_summ)
    }

    //добавить товар
    fun add(item: Dish): Boolean {
        val dublicate: Dish? = dishes.filter { it.id == item.id }.firstOrNull()
        dublicate?.let { return false }

        item.count++
        dishes.add(item)
        increaseSum(item.price)
        return true
    }

    fun getDishes(): MutableList<Dish> = dishes

    fun increaseSum(price: Int) {
        _summ += price
        setSumm()
    }

    fun decreaseSum(price: Int) {
        _summ -= price
        setSumm()
    }

}