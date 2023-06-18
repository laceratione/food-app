package com.example.foodapp.presentation.dishes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.transaction
import com.example.domain.model.DishType
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentCategoryDishesBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.CornerFamily

class CategoryDishes(private val dishType: DishType): Fragment() {
    private val sharedViewModel: CatDishesViewModel by activityViewModels(){
        CatDishesModelFactory(requireActivity().application)
    }
    lateinit var adapter: CategoryDishesAdapter
    private var tags: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCategoryDishesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_category_dishes, container, false)
        adapter = CategoryDishesAdapter(requireContext())
        binding.apply {
            viewModel = sharedViewModel
            categoryDishesAdapter = adapter
            tvCategoryDishes.text = dishType.name
        }
        binding.lifecycleOwner = viewLifecycleOwner

        sharedViewModel.isBackPressed.observe(requireActivity(), {
            if (it)
                fragmentManager?.popBackStack()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tags: List<String> = listOf("Все меню", "Салаты", "С рисом", "С рыбой")
        val chipGroup: ChipGroup? = getView()?.findViewById(R.id.chipGroup)
        tags.forEach { tagName ->
            chipGroup?.addView(createChip(tagName))
        }

        val chip: Chip? = chipGroup?.getChildAt(0) as Chip?
        chip?.isChecked = true

//        val dishes = sharedViewModel.filterDishes(mutableListOf("Все меню"))
//        this.tags.add("Все меню")
//        adapter.updateItems(dishes)
    }

    private fun createChip(name: String): Chip{
        return Chip(context).apply {
            text = name
            textAlignment =  View.TEXT_ALIGNMENT_CENTER
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
                val list: List<String> = tags
                //добавить после заргрузки данных
                val dishes = sharedViewModel.filterDishes(tags)
                adapter.updateItems(dishes)
            }
        }
    }

}