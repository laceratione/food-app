package com.example.foodapp.presentation.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Dish
import com.example.foodapp.R

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val favorites: List<Dish> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recview_favorite_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = favorites.get(position)
        with(holder){
            picture.setImageBitmap(item.bitmap)
            name.setText(item.name)
            price.setText(item.price.toString())
            weight.setText(item.weight.toString())
        }
    }

    override fun getItemCount(): Int = favorites.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val picture: ImageView = view.findViewById(R.id.ivFavorites)
        val name: TextView = view.findViewById(R.id.nameFavoritesItem)
        val price: TextView = view.findViewById(R.id.priceFavoritesItem)
        val weight: TextView = view.findViewById(R.id.weightFavoritesItem)
    }
}