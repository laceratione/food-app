package com.example.foodapp.di

import com.example.foodapp.presentation.dishes.CatDishesViewModel
import com.example.foodapp.presentation.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class, UseCaseModule::class])
interface AppComponent {
    fun inject(homeViewModel: HomeViewModel)
    fun inject(catDishesViewModel: CatDishesViewModel)
}