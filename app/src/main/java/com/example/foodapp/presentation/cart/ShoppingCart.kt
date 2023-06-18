package com.example.foodapp.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentShoppingCartBinding
import com.example.domain.model.Cart

class ShoppingCart: Fragment() {
    private val sharedViewModel: ShoppingCartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindind: FragmentShoppingCartBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_cart, container, false)
        val adapter = ShoppingCartAdapter()
        bindind.apply {
            shopCartViewModel = sharedViewModel
            shoppingCartAdapter = adapter
            //можно создать поля и перенести во viewModel
            cart = Cart
        }
        bindind.lifecycleOwner = viewLifecycleOwner

        return bindind.root
    }
}