package com.example.appcardapio.login.data.remote

import com.example.appcardapio.login.model.UserAuth

interface LoginRemoteDataSource {
    suspend fun createAccount(userName: String, email: String, password: String): UserAuth
    suspend fun signIn(email: String, password: String): UserAuth
    suspend fun recover(email: String)
    fun isSessionValid(): Boolean
}