package com.example.appcardapio.order.data

import com.example.appcardapio.order.model.OrderItem
import com.example.appcardapio.order.data.remote.OrderRemoteDataSource

class OrderRepositoryImpl(
    private val orderRemoteDataSource: OrderRemoteDataSource
): OrderRepository {
    override suspend fun getOrderItems(): List<OrderItem> {
        return orderRemoteDataSource.getOrderItems()
    }

    override suspend fun addOrderItem(name: String, price: String, amount: Int) {
        orderRemoteDataSource.addOrderItem(name, price, amount)
    }

    override suspend fun deleteOrderItem() {
        orderRemoteDataSource.deleteOrderItem()
    }

    override suspend fun updateOrderItem() {
        orderRemoteDataSource.updateOrderItem()
    }
}