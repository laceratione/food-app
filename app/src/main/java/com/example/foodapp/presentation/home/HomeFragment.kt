package com.example.foodapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.model.DishType
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentHomeBinding
import com.facebook.shimmer.ShimmerFrameLayout

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val sharedViewModel: HomeViewModel by activityViewModels()
    private lateinit var categListener: DishTypeAdapter.OnItemCategClickListener

    private lateinit var shimmerLayout: ShimmerFrameLayout

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

        shimmerLayout = binding.shimmerLayout

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sharedViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is HomeUiState.Success -> {
                        adapter.updateItems(uiState.types)
                        stopShimmer()
                    }
                    is HomeUiState.Error -> {
                        showError(uiState.exception)
                        stopShimmer()
                    }
                    is HomeUiState.Loading -> {
                        shimmerLayout.startShimmer()
                        shimmerLayout.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showError(t: Throwable) {
        Toast.makeText(context, getString(R.string.error_load_data), Toast.LENGTH_LONG).show()
    }

    private fun stopShimmer(){
        shimmerLayout.stopShimmer()
        shimmerLayout.visibility = View.GONE
    }
}