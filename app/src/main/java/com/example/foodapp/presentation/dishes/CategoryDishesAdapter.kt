package com.example.foodapp.presentation.dishes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.foodapp.R
import com.example.domain.model.Dish

class CategoryDishesAdapter(private val context: Context): BaseAdapter() {
    private var dishes: List<Dish> = emptyList()
    private var layoutInflater: LayoutInflater? = null
    lateinit var alertDialogInfoDish: AlertDialogInfoDish

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
            alertDialogInfoDish = AlertDialogInfoDish(context, layoutInflater!!, dishes[pos])
            alertDialogInfoDish.createAlertDialog()
        }

        return grid!!
    }

    fun updateItems(items: List<Dish>?) {
        dishes = items ?: emptyList()
        notifyDataSetChanged()
    }

}