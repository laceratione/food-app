package com.example.foodapp.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Dish
import com.example.domain.model.Favorites
import com.example.foodapp.databinding.RecviewFavoriteItemBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val favorites: MutableList<Dish> = Favorites.getFavorites()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecviewFavoriteItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = favorites.get(position)
        with(holder) {
            picture.setImageBitmap(item.bitmap)
            name.setText(item.name)
            price.setText(item.price.toString() + " ₽")
            weight.setText("· " + item.weight.toString() + "г")

            btnDelete.setOnClickListener {
                Favorites.delete(item)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = favorites.size

    class ViewHolder(
        binding: RecviewFavoriteItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val picture: ImageView = binding.ivFavorites
        val name: TextView = binding.nameFavoritesItem
        val price: TextView = binding.priceFavoritesItem
        val weight: TextView = binding.weightFavoritesItem

        val btnDelete: ImageButton = binding.btnDeleteFavoritesItem
    }
}