package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.presentation.home.HomeFragmentDirections
import com.example.foodapp.presentation.home.HomeViewModel
import com.example.foodapp.presentation.home.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val homeViewModel: HomeViewModel = ViewModelProvider(
            this, HomeViewModelFactory(application)
        ).get(HomeViewModel::class.java)
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)


        homeViewModel.isLoadingDishesLive.observe(this, {
            if (it) {
                val name = homeViewModel.selectCategory.name
                val action = HomeFragmentDirections.actionHomeFragmentToCategoryDishes(name)
                navController.navigate(action)
            }
        })
    }

}