package com.example.appcardapio.order.di

import com.example.appcardapio.order.presentation.OrderViewModel
import com.example.appcardapio.order.data.OrderRepository
import com.example.appcardapio.order.data.OrderRepositoryImpl
import com.example.appcardapio.order.data.remote.OrderRemoteDataSource
import com.example.appcardapio.order.data.remote.OrderRemoteDataSourceImpl
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val orderModule = module {
    single<OrderRemoteDataSource> {
        OrderRemoteDataSourceImpl(firebaseFirestore = FirebaseFirestore.getInstance())
    }

    factory<OrderRepository> {
        OrderRepositoryImpl(orderRemoteDataSource = get())
    }

    viewModel {
        OrderViewModel(orderRepository = get())
    }
}