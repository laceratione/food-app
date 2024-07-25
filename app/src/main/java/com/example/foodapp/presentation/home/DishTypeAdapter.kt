package com.example.foodapp.presentation.home

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.DishType
import com.example.foodapp.databinding.RecviewDishItemBinding

class DishTypeAdapter(private val listener: OnItemCategClickListener) :
    RecyclerView.Adapter<DishTypeAdapter.ViewHolder>() {
    private var dishTypes: List<DishType> = emptyList()

    interface OnItemCategClickListener {
        fun onItemClick(type: DishType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecviewDishItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val bitmap: Bitmap? = dishTypes[position].bitmap

            with(holder) {
                ivPicture.setImageBitmap(bitmap)
                tvTitle.text = dishTypes[position].name
                itemView.setOnClickListener {
                    listener.onItemClick(dishTypes[position])
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount() = dishTypes.size

    fun updateItems(items: List<DishType>?) {
        dishTypes = items ?: emptyList()
        notifyDataSetChanged()
    }

    class ViewHolder(
        binding: RecviewDishItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val ivPicture: ImageView = binding.ivDishType
        val tvTitle: TextView = binding.tvDishType
    }
}