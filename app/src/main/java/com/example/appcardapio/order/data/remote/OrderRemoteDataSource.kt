package com.example.appcardapio.order.data.remote

import com.example.appcardapio.order.model.OrderItem
import kotlinx.coroutines.flow.StateFlow

interface OrderRemoteDataSource {
    suspend fun getOrderItems(): List<OrderItem>
    suspend fun addOrderItem(name: String, price: String, amount: Int)
    suspend fun deleteOrderItem(orderItem: OrderItem)
    suspend fun updateOrderItem(orderItem: OrderItem, newAmount: Int)
    fun getOrderItemState(): StateFlow<List<OrderItem>>
}