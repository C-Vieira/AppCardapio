package com.example.appcardapio.order.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcardapio.order.data.OrderRepository
import com.example.appcardapio.order.model.OrderItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class OrderViewModel(
    private val orderRepository: OrderRepository
): ViewModel() {
    private var _orderItemsState = MutableStateFlow(listOf(OrderItem()))
    val orderItemsState: StateFlow<List<OrderItem>> = _orderItemsState.asStateFlow()

    fun getOrderItemSource(): List<OrderItem> = runBlocking {
        orderRepository.getOrderItems()
    }

    fun onOrderViewCreated(){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _orderItemsState.update { orderRepository.getOrderItemState().value }
            }.onFailure { e->

                Log.e("ORDER", e.message ?: "unknown", e)
            }
        }
    }

    fun onIncrementOrDecrementButtonClicked(orderItem: OrderItem, newAmount: Int){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                orderRepository.updateOrderItem(orderItem, newAmount)
            }.onFailure { e->

                Log.e("ORDER", e.message ?: "unknown", e)
            }
        }
    }

    fun onDeleteButtonClicked(orderItem: OrderItem){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                orderRepository.deleteOrderItem(orderItem)
            }.onFailure { e->

                Log.e("ORDER", e.message ?: "unknown", e)
            }
        }
    }
}