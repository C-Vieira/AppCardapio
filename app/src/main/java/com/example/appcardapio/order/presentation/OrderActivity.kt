package com.example.appcardapio.order.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcardapio.databinding.OrderViewBinding
import com.example.appcardapio.order.model.OrderItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderActivity: AppCompatActivity() {
    private lateinit var binding: OrderViewBinding
    private val viewModel: OrderViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = OrderViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.orderItemsRecylerview.layoutManager = layoutManager

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.onOrderViewCreated()
                viewModel.orderItemsState.collect { orderItems ->
                    val adapter = OrderItemAdapter(
                        orderItems,
                        ::onIncrementOrDecrementButtonClicked,
                        ::onDeleteButtonClicked
                    )
                    binding.orderItemsRecylerview.adapter = adapter
                }
            }
        }

        binding.logoutButton.setOnClickListener {
            finish()
        }

        binding.confirmOrderButton.setOnClickListener {
            Snackbar.make(findViewById(android.R.id.content), "Pedido Confirmado com Sucesso!", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun onIncrementOrDecrementButtonClicked(orderItem: OrderItem, i: Int) {
        viewModel.onIncrementOrDecrementButtonClicked(orderItem, i)
    }

    private fun onDeleteButtonClicked(orderItem: OrderItem) {
        viewModel.onDeleteButtonClicked(orderItem)
    }
}