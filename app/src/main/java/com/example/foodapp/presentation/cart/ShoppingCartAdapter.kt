package com.example.foodapp.presentation.cart

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Cart
import com.example.domain.model.Dish
import com.example.foodapp.databinding.RecviewShoppingCartItemBinding

class ShoppingCartAdapter() : RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {
    private var dishes: MutableList<Dish> = Cart.getDishes()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecviewShoppingCartItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishes.get(position)
        val bitmap: Bitmap? = dish.bitmap

        with(holder) {
            picture.setImageBitmap(bitmap)
            name.text = dish.name
            price.text = dish.price.toString() + " ₽"
            weight.text = "· " + dish.weight.toString() + "г"
            count.text = dish.count.toString()

            btnRemove.setOnClickListener {
                Cart.decreaseSum(dish.price)
                var count = Integer.valueOf(holder.count.text.toString())
                holder.count.text = (--count).toString()
                dishes.get(position).count = count

                if (count == 0) {
                    dishes.remove(dishes.get(position))
                    updateItems(dishes)
                }
            }

            btnAdd.setOnClickListener {
                Cart.increaseSum(dish.price)
                var count = Integer.valueOf(holder.count.text.toString())
                holder.count.text = (++count).toString()
                dishes.get(position).count = count
            }
        }
    }

    override fun getItemCount(): Int = dishes.size

    fun updateItems(items: MutableList<Dish>?) {
        items?.let { dishes = it }
        notifyDataSetChanged()
    }

    class ViewHolder(
        binding: RecviewShoppingCartItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val picture: ImageView = binding.ivShopCartItem
        val name: TextView = binding.nameShopCartItem
        val price: TextView = binding.priceShopCartItem
        val weight: TextView = binding.weightShopCartItem
        val count: TextView = binding.countShopCartItem

        val btnRemove: ImageView = binding.btnRemove
        val btnAdd: ImageView = binding.btnAdd
    }
}