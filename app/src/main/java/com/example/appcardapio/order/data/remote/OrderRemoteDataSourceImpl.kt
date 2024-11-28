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
        // Check for duplicates
        val orderItemQuery = orderItemsCollectionRef
            .whereEqualTo("name", name)
            .get()
            .await()

        if(orderItemQuery.documents.isEmpty()){
            // Add OrderItem to Firestore
            firebaseFirestore.collection("orderItems")
                .add(hashMapOf(
                    "name" to name,
                    "price" to price,
                    "amount" to amount
                )).await()
        }else{
            // Increment the amount by one
            for(document in orderItemQuery){
                val currentAmount = document.get("amount").toString().toInt()
                updateOrderItem(OrderItem(name, price, currentAmount), currentAmount + 1)
            }
        }
    }

    override suspend fun deleteOrderItem(orderItem: OrderItem) {
        val orderItemQuery = orderItemsCollectionRef
            .whereEqualTo("name", orderItem.name)
            .whereEqualTo("price", orderItem.price)
            .whereEqualTo("amount", orderItem.amount)
            .get()
            .await()

        if(orderItemQuery.documents.isNotEmpty()){
            for(document in orderItemQuery){
                orderItemsCollectionRef.document(document.id).delete().await()
            }
        }
    }

    override suspend fun updateOrderItem(orderItem: OrderItem, newAmount: Int) {
        val orderItemQuery = orderItemsCollectionRef
            .whereEqualTo("name", orderItem.name)
            .whereEqualTo("price", orderItem.price)
            .whereEqualTo("amount", orderItem.amount)
            .get()
            .await()

        if(orderItemQuery.documents.isNotEmpty()){
            if(newAmount > 0){ // Disallow zero or negative amounts
                for(document in orderItemQuery){
                    orderItemsCollectionRef.document(document.id).update("amount", newAmount).await()
                }
            }
        }
    }
}