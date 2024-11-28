package com.example.appcardapio.order.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcardapio.order.data.OrderRepository
import com.example.appcardapio.order.model.OrderItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class OrderViewModel(
    private val orderRepository: OrderRepository
): ViewModel() {
    private val _uiAction = MutableSharedFlow<OrderAction>()
    val uiAction: SharedFlow<OrderAction> = _uiAction.asSharedFlow()

    fun getOrderItemSource(): List<OrderItem> = runBlocking {
        orderRepository.getOrderItems()
    }

    fun onIncrementOrDecrementButtonClicked(orderItem: OrderItem, newAmount: Int){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                orderRepository.updateOrderItem(orderItem, newAmount)
                _uiAction.emit(OrderAction.UPDATE_UI)
            }.onFailure { e->
                _uiAction.emit(OrderAction.SHOW_ERROR_MSG)
                Log.e("ORDER", e.message ?: "unknown", e)
            }
        }
    }

    fun onDeleteButtonClicked(orderItem: OrderItem){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                orderRepository.deleteOrderItem(orderItem)
                _uiAction.emit(OrderAction.SHOW_ORDER_ITEM_DELETED_MSG)
                _uiAction.emit(OrderAction.UPDATE_UI)
            }.onFailure { e->
                _uiAction.emit(OrderAction.SHOW_ERROR_MSG)
                Log.e("ORDER", e.message ?: "unknown", e)
            }
        }
    }

    fun onConfirmOrderClicked(){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                if(getOrderItemSource().isNotEmpty()){
                    orderRepository.clearOrderItems()
                    _uiAction.emit(OrderAction.SHOW_ORDER_CONFIRMED_MSG)
                    _uiAction.emit(OrderAction.UPDATE_UI)
                }
                else _uiAction.emit(OrderAction.SHOW_EMPTY_ORDER_MSG)
            }.onFailure { e ->
                _uiAction.emit(OrderAction.SHOW_ERROR_MSG)
                Log.e("ORDER", e.message ?: "unknown", e)
            }
        }
    }
}