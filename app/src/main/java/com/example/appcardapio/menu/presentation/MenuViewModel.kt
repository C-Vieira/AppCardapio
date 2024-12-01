package com.example.appcardapio.menu.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcardapio.menu.data.MenuRepository
import com.example.appcardapio.menu.model.MenuItem
import com.example.appcardapio.order.data.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MenuViewModel(
    private val menuRepository: MenuRepository,
    private val orderRepository: OrderRepository
): ViewModel() {
    private var _currentItemState = MutableStateFlow(MenuItem())
    val currentItemState: StateFlow<MenuItem> = _currentItemState.asStateFlow()

    private val _uiAction = MutableSharedFlow<MenuAction>()
    val uiAction: SharedFlow<MenuAction> = _uiAction.asSharedFlow()

    fun getMenuItemSource(): List<MenuItem> = runBlocking {
        menuRepository.getMenuItems()
    }

    fun onAddToOrderClicked(name: String, price: String, amount: Int){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                orderRepository.addOrderItem(name, price, amount)
                _uiAction.emit(MenuAction.SHOW_ADDED_TO_ORDER_MSG)
            }.onFailure { e ->
                _uiAction.emit(MenuAction.SHOW_ERROR_MSG)
                Log.e("MENU", e.message ?: "unknown", e)
            }
        }
    }

    fun onMenuItemClicked(selectedItem: MenuItem){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _uiAction.emit(MenuAction.NAVIGATE_TO_DETAILS)
                menuRepository.setCurrentSelectedItem(selectedItem)
            }.onFailure { e ->
                _uiAction.emit(MenuAction.SHOW_ERROR_MSG)
                Log.e("MENU", e.message ?: "unknown", e)
            }
        }
    }

    fun onOrderClicked(){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _uiAction.emit(MenuAction.NAVIGATE_TO_ORDER)
            }.onFailure { e ->
                _uiAction.emit(MenuAction.SHOW_ERROR_MSG)
                Log.e("MENU", e.message ?: "unknown", e)
            }
        }
    }

    fun onDetailsViewCreated(){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _currentItemState.update { menuRepository.getCurrentSelectedItem() }
            }.onFailure { e ->
                _uiAction.emit(MenuAction.SHOW_ERROR_MSG)
                Log.e("MENU", e.message ?: "unknown", e)
            }
        }
    }

    fun getMenuItemImage(name: String) = runBlocking {
        menuRepository.getMenuItemImage(name)
    }
}