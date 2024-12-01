package com.example.appcardapio.menu.model

import com.example.appcardapio.R

open class MenuItem(
    val name: String = "unknown",
    val description: String = "unknown",
    val category: String = "unknown",
    val price: String = "unknown"
) {
    val icon: Int get() = when(category){
        "festa" -> R.drawable.ic_cookie
        "copos" -> R.drawable.ic_ice_cream
        else -> R.drawable.ic_food
    }
}