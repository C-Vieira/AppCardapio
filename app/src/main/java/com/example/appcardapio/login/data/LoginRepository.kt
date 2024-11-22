package com.example.appcardapio.login.data

import com.example.appcardapio.login.model.UserAuth

interface LoginRepository {
    fun isSessionValid(): Boolean
    suspend fun createAccount(userName: String, email: String, password: String): UserAuth
    suspend fun login(email: String, password: String): UserAuth
    suspend fun recover(email: String)
}