package com.example.foodapp.di

import com.example.foodapp.presentation.dishes.CatDishesViewModel
import com.example.foodapp.presentation.home.HomeViewModel
import dagger.Component

@Component(modules = [NetworkModule::class, RepositoryModule::class, UseCaseModule::class])
interface AppComponent {
    fun inject(homeViewModel: HomeViewModel)
    fun inject(catDishesViewModel: CatDishesViewModel)
}