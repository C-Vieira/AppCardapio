package com.example.appcardapio.order.model

class OrderItem (
    val name: String = "unknown",
    val price: String = "0.0",
    val amount : Int = 0,
) {
    val totalPrice get() = this.amount * this.price.toDouble()
}