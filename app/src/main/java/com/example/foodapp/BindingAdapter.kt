package com.example.foodapp

import android.content.Context
import android.widget.GridView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Dish
import com.example.domain.model.DishType
import com.example.foodapp.presentation.dishes.CategoryDishesAdapter
import com.example.foodapp.presentation.home.DishTypeAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

//обновление категорий блюд главного экрана
@BindingAdapter("dishTypes", "categListener")
fun bindDishTypes(recView: RecyclerView, itemViewModels: List<DishType>?, listener: DishTypeAdapter.OnItemCategClickListener) {
    recView.layoutManager =
        LinearLayoutManager(recView.context, LinearLayoutManager.VERTICAL, false)
    val adapter = createAdapter(recView, listener)
    adapter.updateItems(itemViewModels)
}

//создание DishTypeAdapter
private fun createAdapter(recyclerView: RecyclerView, listener: DishTypeAdapter.OnItemCategClickListener): DishTypeAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is DishTypeAdapter) {
        recyclerView.adapter as DishTypeAdapter
    } else {
        val bindableAdapter = DishTypeAdapter(listener)
        recyclerView.adapter = bindableAdapter
        bindableAdapter
    }
}

@BindingAdapter("onNavigationItemSelected")
fun setOnNavigationItemSelectedListener(
    view: BottomNavigationView,
    listener: BottomNavigationView.OnNavigationItemSelectedListener
) {
    view.setOnNavigationItemSelectedListener(listener)
}

//обновление блюд выбранной категории
@BindingAdapter("dataCategoryDishes", "myContext")
fun bindGridView(gridView: GridView, itemViewModels: List<Dish>?, context: Context) {
    val adapter = createGridViewAdapter(gridView, context)
    adapter.updateItems(itemViewModels)
}

//создание CategoryDishesAdapter
private fun createGridViewAdapter(gridView: GridView, context: Context): CategoryDishesAdapter {
    return if (gridView.adapter != null && gridView.adapter is CategoryDishesAdapter) {
        gridView.adapter as CategoryDishesAdapter
    } else {
        val bindableGridViewAdapter = CategoryDishesAdapter(context)
        gridView.adapter = bindableGridViewAdapter
        bindableGridViewAdapter
    }
}