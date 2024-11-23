package com.example.appcardapio

import com.example.appcardapio.menu.model.MenuItem

class OrderItem (
    imageUrl: String,
    icon: Int?,
    name: String,
    description: String,
    category: String,
    price: String,
    val amount : Int,
): MenuItem(imageUrl ,icon, name, description, category, price){
    val totalPrice get() = this.amount * this.price.toDouble()
}