package com.example.foodapp.presentation.dishes

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.example.foodapp.R
import com.example.domain.model.Cart
import com.example.domain.model.Dish

class CategoryDishesAdapter(private val context: Context): BaseAdapter() {
    private var dishes: List<Dish> = emptyList()
    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int = dishes.size

    override fun getItem(p0: Int): Any = dishes[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
        var grid = convertView

        if (layoutInflater == null){
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                    as LayoutInflater
        }

        if (grid == null){
            grid = layoutInflater?.inflate(R.layout.gridview_dish_item, null)
        }

        val picture: ImageView? = grid?.findViewById(R.id.ivGridItem)
        val name: TextView? = grid?.findViewById(R.id.tvGridItem)

        picture?.setImageBitmap(dishes[pos].bitmap)
        name?.setText(dishes[pos].name)

        //просмотр детальной информации блюда
        grid?.setOnClickListener{
            createAlertDialog(context, layoutInflater!!, dishes[pos])
        }

        return grid!!
    }

    fun updateItems(items: List<Dish>?) {
        dishes = items ?: emptyList()
        notifyDataSetChanged()
    }

    fun createAlertDialog(context: Context, inflater: LayoutInflater, dish: Dish){
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
        btnClose.setOnClickListener {dialog.cancel()}

        //добавить в корзину
        val btnAddToCart: AppCompatButton = myView.findViewById(R.id.btnAddToCart)
        btnAddToCart.setOnClickListener{
            val isAdd = Cart.add(dish)
            if (isAdd)
                showMessage("Товар добавлен в корзину")
            else
                showMessage("Товар уже есть в корзине")
        }

        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    //вынести во фрагмент
    fun showMessage(text: String){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}