package com.example.foodapp

import android.app.Application
import com.example.foodapp.di.AppComponent
import com.example.foodapp.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()
    }
}