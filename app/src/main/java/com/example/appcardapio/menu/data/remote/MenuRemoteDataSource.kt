package com.example.appcardapio.menu.data.remote

import android.graphics.Bitmap
import com.example.appcardapio.menu.model.MenuItem

interface MenuRemoteDataSource {
    suspend fun getMenuItems(): List<MenuItem>
    suspend fun setCurrentSelectedItem(item: MenuItem)
    suspend fun getCurrentSelectedItem(): MenuItem
    suspend fun getMenuItemImage(imageName: String): Bitmap?
}