package com.example.appcardapio.order.data.remote

import com.example.appcardapio.order.model.OrderItem
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class OrderRemoteDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
): OrderRemoteDataSource {
    private val orderItemsCollectionRef: CollectionReference = firebaseFirestore.collection("orderItems")

    override suspend fun getOrderItems(): List<OrderItem> {
        val collection = orderItemsCollectionRef.get().await()
        val orderItems = mutableListOf<OrderItem>()

        for(orderItem in collection.documents){
            orderItem.toObject<OrderItem>()?.let { orderItems.add(it) }
        }

        return orderItems
    }

    override suspend fun addOrderItem(name: String, price: String, amount: Int) {
        // Add OrderItem to Firestore
        firebaseFirestore.collection("orderItems")
            .add(hashMapOf(
                "name" to name,
                "price" to price,
                "amount" to amount
            )).await()
    }

    override suspend fun deleteOrderItem() {

    }

    override suspend fun updateOrderItem() {

    }
}