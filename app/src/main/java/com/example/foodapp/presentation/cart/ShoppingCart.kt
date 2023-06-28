package com.example.foodapp.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.foodapp.databinding.FragmentShoppingCartBinding
import com.example.domain.model.Cart

class ShoppingCart: Fragment() {
    private lateinit var binding: FragmentShoppingCartBinding
    private val sharedViewModel: ShoppingCartViewModel by activityViewModels()
    private lateinit var adapter: ShoppingCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentShoppingCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ShoppingCartAdapter()
        binding.rvShoppingCrt.adapter = adapter

        binding.btnPay.text = String.format("Оплатить %s ₽", Cart.summLive.value.toString())
        binding.btnPay.setOnClickListener {
            sharedViewModel.pay()
        }

        Cart.summLive.observe(viewLifecycleOwner, {
            binding.btnPay.text = String.format("Оплатить %s ₽", it.toString())
        })
    }

}