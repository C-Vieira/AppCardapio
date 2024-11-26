package com.example.appcardapio.order.data.remote

import com.example.appcardapio.order.model.OrderItem
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await

class OrderRemoteDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
): OrderRemoteDataSource {
    private val orderItemsCollectionRef: CollectionReference = firebaseFirestore.collection("orderItems")

    private var _orderItemsDataSourceState = MutableStateFlow(listOf<OrderItem>())
    //val orderItemsDataSourceState: StateFlow<List<OrderItem>> = _orderItemsDataSourceState.asStateFlow()

    init {
        subscribeToRealTimeUpdates()
    }

    override fun getOrderItemState(): StateFlow<List<OrderItem>> {
        return _orderItemsDataSourceState
    }

    override suspend fun getOrderItems(): List<OrderItem> {
        val collection = orderItemsCollectionRef.get().await()
        val orderItems = mutableListOf<OrderItem>()

        for(orderItem in collection.documents){
            orderItem.toObject<OrderItem>()?.let { orderItems.add(it) }
        }

        return orderItems
    }

    private fun subscribeToRealTimeUpdates() {
        orderItemsCollectionRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                return@addSnapshotListener
            }
            querySnapshot?.let { snapshot ->
                val newOrderItems = mutableListOf(OrderItem())
                for(orderItem in snapshot){
                    orderItem.toObject<OrderItem>().let { newOrderItems.add(it) }
                }

                _orderItemsDataSourceState.update { newOrderItems }
            }
        }
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
            for(document in orderItemQuery){
                orderItemsCollectionRef.document(document.id).update("amount", newAmount).await()
            }
        }
    }
}