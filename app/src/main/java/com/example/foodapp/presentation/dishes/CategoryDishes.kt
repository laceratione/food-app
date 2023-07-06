package com.example.foodapp.presentation.dishes

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentCategoryDishesBinding
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

        //добавить extension
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sharedViewModel.dataCategoryDishes.collect {
                adapter.updateItems(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sharedViewModel.isCategoryDishesLoading.collect {
                binding.pbCategoryDishes.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            sharedViewModel.isBackPressed.collect {}
        }

    }

    private fun initChipGroup() {
        val tags: List<String> = listOf("Все меню", "Салаты", "С рисом", "С рыбой")
        val chipGroup: ChipGroup? = getView()?.findViewById(R.id.chipGroup)
        tags.forEach { tagName ->
            chipGroup?.addView(createChip(tagName))
        }

        val chip: Chip? = chipGroup?.getChildAt(0) as Chip?
        chip?.isChecked = true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createChip(name: String): Chip {
        return Chip(context).apply {
            text = name
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setChipBackgroundColorResource(R.color.chip_tab_item_foreground)
            setCheckedIconVisible(false)
            setCloseIconVisible(false)
            setTextColor(resources.getColorStateList(R.color.chip_tab_item_text, null))
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


}