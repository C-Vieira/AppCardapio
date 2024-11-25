package com.example.appcardapio.menu.data.remote

import com.example.appcardapio.menu.model.MenuItem
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class MenuRemoteDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
): MenuRemoteDataSource {
    private var currentSelectedItem: MenuItem = MenuItem()
    private val menuItemsCollectionRef: CollectionReference = firebaseFirestore.collection("menuItems")

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