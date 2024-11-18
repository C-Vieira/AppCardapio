package com.example.appcardapio.login.model

import android.net.Uri

data class UserAuth(
    val uid: String,
    val displaName: String?,
    val email: String?,
    val photoUrl: Uri?,
    val phoneNumber: String?,
    val isEmialVerified: Boolean
)