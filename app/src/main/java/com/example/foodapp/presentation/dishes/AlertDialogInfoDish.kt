package com.example.foodapp.presentation.dishes

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.example.domain.model.Cart
import com.example.domain.model.Dish
import com.example.foodapp.R

class AlertDialogInfoDish(
    private val context: Context,
    private val inflater: LayoutInflater,
    private val dish: Dish
) {
    fun createAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val myView: View = inflater.inflate(R.layout.info_dish, null)

        val image: ImageView = myView.findViewById(R.id.ivInfoDish)
        val name: TextView = myView.findViewById(R.id.nameInfoDish)
        val price: TextView = myView.findViewById(R.id.priceInfoDish)
        val weight: TextView = myView.findViewById(R.id.weightInfoDish)
        val description: TextView = myView.findViewById(R.id.descriptionInfoDish)

        image.setImageBitmap(dish.bitmap)
        name.setText(dish.name)
        price.setText(dish.price.toString() + " ₽")
        weight.setText("· " + dish.weight.toString() + "г")
        description.setText(dish.description)

        builder.setView(myView)
        val dialog: AlertDialog = builder.create()

        //закрыть окно
        val btnClose: ImageButton = myView.findViewById(R.id.btnClose)
        btnClose.setOnClickListener { dialog.cancel() }

        //добавить в корзину
        val btnAddToCart: AppCompatButton = myView.findViewById(R.id.btnAddToCart)
        btnAddToCart.setOnClickListener {
            val isAdd = Cart.add(dish)
            if (isAdd)
                showMessage("Товар добавлен в корзину")
            else
                showMessage("Товар уже есть в корзине")
        }

        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun showMessage(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}