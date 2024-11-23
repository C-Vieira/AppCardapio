package com.example.appcardapio

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcardapio.databinding.OrderViewBinding
import com.google.android.material.snackbar.Snackbar

class OrderActivity: Activity() {
    private lateinit var binding: OrderViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = OrderViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderItemsSource = mutableListOf(
            OrderItem("", R.drawable.ic_food,"Item1", "Test", "unknown", "9.99", 1),
            OrderItem("", R.drawable.ic_food,"Item2", "Test", "unknown", "9.99", 2),
            OrderItem("", R.drawable.ic_food,"Item3", "Test", "unknown", "9.99", 3)
        )

        val adapter = OrderItemAdapter(orderItemsSource, ::onListItemClicked)
        val layoutManager = LinearLayoutManager(this)

        binding.orderItemsRecylerview.adapter = adapter
        binding.orderItemsRecylerview.layoutManager = layoutManager

        binding.logoutButton.setOnClickListener {
            finish()
        }

        binding.confirmOrderButton.setOnClickListener {
            Snackbar.make(findViewById(android.R.id.content), "Pedido Confirmado com Sucesso!", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun onListItemClicked(orderItem: OrderItem) {

    }
}