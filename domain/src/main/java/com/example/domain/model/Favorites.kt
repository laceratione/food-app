package com.example.domain.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Favorites {
    private val _favorites: MutableList<Dish> = mutableListOf()

    private val _count: MutableLiveData<Int> = MutableLiveData(_favorites.size)
    val count: LiveData<Int> = _count

    fun add(dish: Dish): Boolean {
        val dublicate: Dish? = _favorites.firstOrNull { it.id == dish.id }
        dublicate?.let { return false }

        _favorites.add(dish)
        updateCount()
        return true
    }

    fun delete(dish: Dish){
        _favorites.remove(dish)
        updateCount()
    }

    private fun updateCount(){
        _count.value = _favorites.size
    }

    fun getFavorites() = _favorites
}