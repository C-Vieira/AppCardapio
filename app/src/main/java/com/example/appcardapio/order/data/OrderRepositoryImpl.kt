package com.example.appcardapio.order.data

import com.example.appcardapio.order.model.OrderItem
import com.example.appcardapio.order.data.remote.OrderRemoteDataSource
import kotlinx.coroutines.flow.StateFlow

class OrderRepositoryImpl(
    private val orderRemoteDataSource: OrderRemoteDataSource
): OrderRepository {
    override fun getOrderItemState(): StateFlow<List<OrderItem>> {
        return orderRemoteDataSource.getOrderItemState()
    }

    override suspend fun getOrderItems(): List<OrderItem> {
        return orderRemoteDataSource.getOrderItems()
    }

    override suspend fun addOrderItem(name: String, price: String, amount: Int) {
        orderRemoteDataSource.addOrderItem(name, price, amount)
    }

    override suspend fun deleteOrderItem(orderItem: OrderItem) {
        orderRemoteDataSource.deleteOrderItem(orderItem)
    }

    override suspend fun updateOrderItem(orderItem: OrderItem, newAmount: Int) {
        orderRemoteDataSource.updateOrderItem(orderItem, newAmount)
    }
}