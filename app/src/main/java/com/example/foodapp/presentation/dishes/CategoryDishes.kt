package com.example.foodapp.presentation.dishes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentCategoryDishesBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.CornerFamily

class CategoryDishes() : Fragment() {
    private lateinit var binding: FragmentCategoryDishesBinding
    private val sharedViewModel: CatDishesViewModel by activityViewModels() {
        CatDishesModelFactory(requireActivity().application)
    }
    private val args: CategoryDishesArgs by navArgs()
    private lateinit var adapter: CategoryDishesAdapter
    private var tags: MutableList<String> = mutableListOf()
    private lateinit var shimmerLayout: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentCategoryDishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CategoryDishesAdapter(requireContext())
        binding.gvCategoryDishes.adapter = adapter

        binding.tvCategoryDishes.text = args.nameCat
        binding.btnBack.setOnClickListener { }

        initChipGroup()

        shimmerLayout = binding.shimmerLayout

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sharedViewModel.uiState.collect { uiState ->
                when (uiState) {
                    is CatDishesUiState.Success -> {
                        adapter.updateItems(uiState.types)
                        stopShimmer()
                    }
                    is CatDishesUiState.Error -> {
                        showError(uiState.exception)
                        stopShimmer()
                    }
                    is CatDishesUiState.Loading -> {
                        shimmerLayout.startShimmer()
                        shimmerLayout.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sharedViewModel.isBackPressed.collect {}
        }
    }

    private fun initChipGroup() {
        val tags = resources.getStringArray(R.array.tags).toList()
        val chipGroup: ChipGroup? = getView()?.findViewById(R.id.chipGroup)
        tags.forEach { tagName ->
            chipGroup?.addView(createChip(tagName))
        }

        val chip: Chip? = chipGroup?.getChildAt(0) as Chip?
        chip?.isChecked = true
    }

    private fun createChip(name: String): Chip {
        return Chip(context).apply {
            text = name
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setChipBackgroundColorResource(R.color.chip_tab_item_foreground)
            setCheckedIconVisible(false)
            setCloseIconVisible(false)
            setTextColor(ContextCompat.getColorStateList(context, R.color.chip_tab_item_text))
            isCheckable = true
            isClickable = true
            shapeAppearanceModel = shapeAppearanceModel
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED, 30F)
                .build()
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked)
                    tags.add(name)
                else
                    tags.remove(name)
                val dishes = sharedViewModel.filterDishes(tags)
                adapter.updateItems(dishes)
            }
        }
    }

    private fun showError(t: Throwable) {
        Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
    }

    private fun stopShimmer() {
        shimmerLayout.stopShimmer()
        shimmerLayout.visibility = View.GONE
    }
}