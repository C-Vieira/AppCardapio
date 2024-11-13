package com.example.appcardapio

class OrderItem (
    name: String,
    price: String,
    val amount : Int,
): MenuItem(name, price){
    val totalPrice get() = this.amount * this.price.toDouble()
}