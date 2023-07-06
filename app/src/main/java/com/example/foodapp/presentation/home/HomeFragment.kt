package com.example.foodapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.model.DishType
import com.example.foodapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val sharedViewModel: HomeViewModel by activityViewModels()
    private lateinit var categListener: DishTypeAdapter.OnItemCategClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categListener = object : DishTypeAdapter.OnItemCategClickListener {
            override fun onItemClick(type: DishType) {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToCategoryDishes(nameCat = type.name)
                findNavController().navigate(action)
            }
        }

        val adapter = DishTypeAdapter(categListener)
        binding.rvDishType.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sharedViewModel.dataDishTypes.collect {
                adapter.updateItems(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sharedViewModel.isDishTypesLoading.collect {
                binding.pbDishTypes.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

}