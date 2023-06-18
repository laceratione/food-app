package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.presentation.cart.ShoppingCart
import com.example.foodapp.presentation.dishes.CategoryDishes
import com.example.foodapp.presentation.home.HomeFragment
import com.example.foodapp.presentation.home.HomeViewModel
import com.example.foodapp.presentation.home.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val homeViewModel: HomeViewModel = ViewModelProvider(this, HomeViewModelFactory(application)
        ).get(HomeViewModel::class.java)
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        loadFragment(HomeFragment.newInstance(), false)

        //открытие окон навигации
        homeViewModel.botNavPageLive.observe(this, {
            val fragment: Fragment
            when (it) {
                1 -> {
                    fragment = HomeFragment()
                    loadFragment(fragment, false)
                }
                2 -> {}
                3 -> {
                    fragment = ShoppingCart()
                    loadFragment(fragment, false)
                }
                4 -> {}
            }
        })

        homeViewModel.isLoadingDishesLive.observe(this, {
            if (it)
                loadFragment(CategoryDishes(homeViewModel.selectCategory), true)
        })

    }

    //загрузка фрагмента
    private fun loadFragment(fragment: Fragment, addBackStack: Boolean) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .apply {
                if (addBackStack) this.addToBackStack("")}
            .commit()
    }
}