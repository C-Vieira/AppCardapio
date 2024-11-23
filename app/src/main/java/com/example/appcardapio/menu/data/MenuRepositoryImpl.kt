package com.example.appcardapio.menu.data

import com.example.appcardapio.menu.model.MenuItem
import com.example.appcardapio.menu.data.remote.MenuRemoteDataSource

class MenuRepositoryImpl(
    private val menuRemoteDataSource: MenuRemoteDataSource
): MenuRepository {

    override suspend fun getMenuItems(): List<MenuItem> {
        return menuRemoteDataSource.getMenuItems()
    }

    override suspend fun setCurrentSelectedItem(item: MenuItem) {
        menuRemoteDataSource.setCurrentSelectedItem(item)
    }

    override suspend fun getCurrentSelectedItem(): MenuItem {
        return menuRemoteDataSource.getCurrentSelectedItem()
    }
}