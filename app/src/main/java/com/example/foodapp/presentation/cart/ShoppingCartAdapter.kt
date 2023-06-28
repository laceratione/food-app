package com.example.foodapp.presentation.cart

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.domain.model.Cart
import com.example.domain.model.Dish

class ShoppingCartAdapter() : RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {
    private var dishes: MutableList<Dish> = Cart.getDishes()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recview_shopping_cart_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishes.get(position)

        val bitmap: Bitmap? = dish.bitmap
        holder.picture.setImageBitmap(bitmap)
        holder.name.text = dish.name
        holder.price.text = dish.price.toString() + " ₽"
        holder.weight.text ="· " + dish.weight.toString() + "г"
        holder.count.text = dish.count.toString()

        holder.btnRemove.setOnClickListener {
            Cart.decreaseSum(dish.price)
            var count = Integer.valueOf(holder.count.text.toString())
            holder.count.text = (--count).toString()
            dishes.get(position).count = count

            if (count == 0) {
                dishes.remove(dishes.get(position))
                updateItems(dishes)
            }
        }

        holder.btnAdd.setOnClickListener {
            Cart.increaseSum(dish.price)
            var count = Integer.valueOf(holder.count.text.toString())
            holder.count.text = (++count).toString()
            dishes.get(position).count = count
        }
    }

    override fun getItemCount(): Int = dishes.size

    fun updateItems(items: MutableList<Dish>?) {
        items?.let { dishes = it }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picture: ImageView = itemView.findViewById(R.id.ivShopCartItem)
        val name: TextView = itemView.findViewById(R.id.nameShopCartItem)
        val price: TextView = itemView.findViewById(R.id.priceShopCartItem)
        val weight: TextView = itemView.findViewById(R.id.weightShopCartItem)
        val count: TextView = itemView.findViewById(R.id.countShopCartItem)

        val btnRemove: ImageView = itemView.findViewById(R.id.btnRemove)
        val btnAdd: ImageView = itemView.findViewById(R.id.btnAdd)
    }
}