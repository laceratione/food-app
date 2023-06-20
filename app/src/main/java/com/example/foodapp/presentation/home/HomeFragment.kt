package com.example.foodapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.domain.model.DishType
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    private val sharedViewModel: HomeViewModel by activityViewModels()
    private lateinit var categListener: DishTypeAdapter.OnItemCategClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        categListener = object : DishTypeAdapter.OnItemCategClickListener{
            override fun onItemClick(type: DishType) {
                sharedViewModel.loadCategoryDishes(type)
            }
        }

        binding.apply {
            homeViewModel = sharedViewModel
            listener = categListener
        }
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

}