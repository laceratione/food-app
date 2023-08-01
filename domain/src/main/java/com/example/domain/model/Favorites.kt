package com.example.domain.model

object Favorites {
    private val favorites: MutableList<Dish> = mutableListOf()
        get() = field

    fun add(dish: Dish): Boolean {
        val dublicate: Dish? = favorites.firstOrNull { it.id == dish.id }
        dublicate?.let { return false }

        favorites.add(dish)
        return true
    }
}