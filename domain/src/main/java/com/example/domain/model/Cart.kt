package com.example.domain.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

//корзина покупок
object Cart : BaseObservable() {
    private var dishes: MutableList<Dish> = mutableListOf()
    private var summ: Int = 0

    @Bindable
    fun getSumm(): Int {
        return summ
    }

    fun setSumm(summ: Int) {
        this.summ = summ
        notifyPropertyChanged(BR.summ)
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

    //удалить товар
    fun remove(item: Dish) {
        item.count--
        dishes.remove(item)
        decreaseSum(item.price)
    }

    fun getDishes(): MutableList<Dish> = dishes

    fun increaseSum(price: Int) {
        summ += price
        setSumm(summ)
    }

    fun decreaseSum(price: Int) {
        summ -= price
        setSumm(summ)
    }

}