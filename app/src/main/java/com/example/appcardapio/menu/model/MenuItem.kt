package com.example.appcardapio.menu.model

import com.example.appcardapio.R

open class MenuItem (
    val imageUrl: String = "",
    val icon: Int? = R.drawable.ic_food,
    val name: String = "unknown",
    val description: String = "unknown",
    val category: String = "unknown",
    val price: String = "unknown"
)