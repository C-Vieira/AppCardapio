package com.example.appcardapio.menu.data

import android.graphics.Bitmap
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

    override suspend fun getMenuItemImage(imageName: String): Bitmap? {
        return menuRemoteDataSource.getMenuItemImage(imageName)
    }
}