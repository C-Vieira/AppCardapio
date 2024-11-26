package com.example.appcardapio.order.data

import com.example.appcardapio.order.model.OrderItem
import kotlinx.coroutines.flow.StateFlow

interface OrderRepository {
    suspend fun getOrderItems(): List<OrderItem>
    suspend fun addOrderItem(name: String, price: String, amount: Int)
    suspend fun deleteOrderItem(orderItem: OrderItem)
    suspend fun updateOrderItem(orderItem: OrderItem, newAmount: Int)
    fun getOrderItemState(): StateFlow<List<OrderItem>>
}