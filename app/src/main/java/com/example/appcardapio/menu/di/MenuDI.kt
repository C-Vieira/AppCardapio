package com.example.appcardapio.menu.di

import com.example.appcardapio.menu.presentation.MenuViewModel
import com.example.appcardapio.menu.data.MenuRepository
import com.example.appcardapio.menu.data.MenuRepositoryImpl
import com.example.appcardapio.menu.data.remote.MenuRemoteDataSource
import com.example.appcardapio.menu.data.remote.MenuRemoteDataSourceImpl
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val menuModule = module {
    single<MenuRemoteDataSource> {
        MenuRemoteDataSourceImpl(firebaseFirestore = FirebaseFirestore.getInstance())
    }

    factory<MenuRepository> {
        MenuRepositoryImpl(menuRemoteDataSource = get())
    }

    viewModel {
        MenuViewModel(menuRepository = get(), orderRepository = get())
    }
}