package com.example.appcardapio.menu.data.remote

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.appcardapio.menu.model.MenuItem
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class MenuRemoteDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
): MenuRemoteDataSource {
    private var currentSelectedItem: MenuItem = MenuItem()
    private val menuItemsCollectionRef: CollectionReference = firebaseFirestore.collection("menuItems")
    private val imageRef = Firebase.storage.reference

    override suspend fun getMenuItemImage(imageName: String): Bitmap? {
        try {
            val maxDownloadSize = 5L * 1024 * 1024
            val menuItemBytes = imageRef.child("images/$imageName.png").getBytes(maxDownloadSize).await()
            val menuItemBitmap = BitmapFactory.decodeByteArray(menuItemBytes, 0, menuItemBytes.size)

            return menuItemBitmap

        }catch (e: Exception){
            Log.e("MENU", e.message ?: "unknown", e)

            return null
        }
    }

    override suspend fun getMenuItems(): List<MenuItem> {
        val collection = menuItemsCollectionRef.get().await()
        val menuItems = mutableListOf<MenuItem>()

        for (menuItem in collection.documents){
            menuItem.toObject<MenuItem>()?.let { menuItems.add(it) }
        }

        return menuItems
    }

    override suspend fun setCurrentSelectedItem(item: MenuItem) {
        currentSelectedItem = item
    }

    override suspend fun getCurrentSelectedItem(): MenuItem {
        return currentSelectedItem
    }
}