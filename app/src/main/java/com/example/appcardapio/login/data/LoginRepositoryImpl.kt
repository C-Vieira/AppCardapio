package com.example.appcardapio.login.data

import android.graphics.Bitmap
import com.example.appcardapio.login.data.remote.LoginRemoteDataSource
import com.example.appcardapio.login.model.UserAuth

class LoginRepositoryImpl(
    private val loginRemoteDataSource: LoginRemoteDataSource
): LoginRepository {
    override fun isSessionValid(): Boolean {
        return loginRemoteDataSource.isSessionValid()
    }

    override suspend fun createAccount(userName: String, email: String, password: String): UserAuth {
        return loginRemoteDataSource.createAccount(userName, email, password)
    }

    override suspend fun login(email: String, password: String): UserAuth {
        return loginRemoteDataSource.signIn(email, password)
    }

    override suspend fun recover(email: String) {
        loginRemoteDataSource.recover(email)
    }

    override suspend fun getLogoImage(): Bitmap? {
        return loginRemoteDataSource.getLogoImage()
    }
}