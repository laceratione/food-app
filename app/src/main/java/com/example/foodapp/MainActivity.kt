package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.domain.model.Favorites
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.presentation.home.HomeViewModel
import com.example.foodapp.presentation.home.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val homeViewModel: HomeViewModel = ViewModelProvider(
            this, HomeViewModelFactory(application)
        ).get(HomeViewModel::class.java)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        //кол-во элементов в Избранном
        Favorites.count.observe(this, {
            val badge = binding.bottomNavigation.getOrCreateBadge(R.id.favoriteFragment)
            badge.isVisible = if (it > 0) true else false
            badge.number = it
        })

        setContentView(binding.root)
    }
}