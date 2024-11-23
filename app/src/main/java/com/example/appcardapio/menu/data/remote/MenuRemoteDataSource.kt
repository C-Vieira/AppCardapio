package com.example.appcardapio.menu.data.remote

import com.example.appcardapio.menu.model.MenuItem

interface MenuRemoteDataSource {
    suspend fun getMenuItems(): List<MenuItem>
    suspend fun setCurrentSelectedItem(item: MenuItem)
    suspend fun getCurrentSelectedItem(): MenuItem
}