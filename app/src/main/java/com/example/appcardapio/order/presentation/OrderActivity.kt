package com.example.appcardapio.order.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcardapio.databinding.OrderViewBinding
import com.example.appcardapio.order.model.OrderItem
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderActivity: AppCompatActivity() {
    private lateinit var binding: OrderViewBinding
    private val viewModel: OrderViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = OrderViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderItemSource = viewModel.getOrderItemSource()

        val adapter = OrderItemAdapter(orderItemSource, ::onListItemClicked)
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