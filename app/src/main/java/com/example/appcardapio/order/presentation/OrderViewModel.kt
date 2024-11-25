package com.example.appcardapio.order.presentation

import androidx.lifecycle.ViewModel
import com.example.appcardapio.order.data.OrderRepository
import com.example.appcardapio.order.model.OrderItem
import kotlinx.coroutines.runBlocking

class OrderViewModel(
    private val orderRepository: OrderRepository
): ViewModel() {
    fun getOrderItemSource(): List<OrderItem> = runBlocking {
        orderRepository.getOrderItems()
    }
}