package com.example.appcardapio.menu.data

import com.example.appcardapio.menu.model.MenuItem

interface MenuRepository {
    suspend fun getMenuItems(): List<MenuItem>
    suspend fun setCurrentSelectedItem(item: MenuItem)
    suspend fun getCurrentSelectedItem(): MenuItem
}