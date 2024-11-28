package com.example.appcardapio.order.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcardapio.databinding.OrderViewBinding
import com.example.appcardapio.order.model.OrderItem
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderActivity: AppCompatActivity() {
    private lateinit var binding: OrderViewBinding
    private val viewModel: OrderViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = OrderViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionManager = OrderActionManager(this)

        val orderItemSource = viewModel.getOrderItemSource()

        val adapter = OrderItemAdapter(
            orderItemSource,
            ::onIncrementOrDecrementButtonClicked,
            ::onDeleteButtonClicked
        )
        binding.orderItemsRecylerview.adapter = adapter

        binding.orderPriceTotal.text = calculateTotalPrice(orderItemSource)

        val layoutManager = LinearLayoutManager(this)
        binding.orderItemsRecylerview.layoutManager = layoutManager

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiAction.collect { action ->
                    actionManager.executeAction(action)
                }
            }
        }

        binding.logoutButton.setOnClickListener {
            finish()
        }

        binding.confirmOrderButton.setOnClickListener {
            viewModel.onConfirmOrderClicked()
        }
    }

    private fun onIncrementOrDecrementButtonClicked(orderItem: OrderItem, i: Int) {
        viewModel.onIncrementOrDecrementButtonClicked(orderItem, i)
    }

    private fun onDeleteButtonClicked(orderItem: OrderItem) {
        viewModel.onDeleteButtonClicked(orderItem)
    }

    fun updateUI(){
        // Get new item source and bind a new adapter
        val orderItemSource = viewModel.getOrderItemSource()

        val adapter = OrderItemAdapter(
            orderItemSource,
            ::onIncrementOrDecrementButtonClicked,
            ::onDeleteButtonClicked
        )
        binding.orderItemsRecylerview.adapter = adapter

        // Re-calculate total order price
        binding.orderPriceTotal.text = calculateTotalPrice(orderItemSource)
    }

    private fun calculateTotalPrice(orderItems: List<OrderItem>): String{
        var total = 0.0
        for (item in orderItems){
            total += item.totalPrice
        }
        return total.toString()
    }
}