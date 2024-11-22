package com.example.appcardapio.login.di

import com.example.appcardapio.login.data.remote.LoginRemoteDataSource
import com.example.appcardapio.login.data.remote.LoginRemoteDataSourceImpl
import com.example.appcardapio.login.data.LoginRepository
import com.example.appcardapio.login.data.LoginRepositoryImpl
import com.example.appcardapio.login.presentation.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory<LoginRemoteDataSource> {
        LoginRemoteDataSourceImpl(firebaseAuth = FirebaseAuth.getInstance(), firebaseFirestore = FirebaseFirestore.getInstance())
    }

    factory<LoginRepository> {
        LoginRepositoryImpl(loginRemoteDataSource = get())
    }

    viewModel {
        LoginViewModel(loginRepository = get())
    }
}