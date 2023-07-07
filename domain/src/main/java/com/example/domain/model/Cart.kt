package com.example.domain.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//корзина покупок
object Cart {
    private var dishes: MutableList<Dish> = mutableListOf()

    private var _summ: Int = 0
    private val _summLive: MutableStateFlow<Int> = MutableStateFlow(0)
    val summLive: StateFlow<Int> = _summLive.asStateFlow()

    fun setSumm() {
        _summLive.value = _summ
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