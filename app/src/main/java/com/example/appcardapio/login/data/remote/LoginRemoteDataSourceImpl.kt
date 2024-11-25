package com.example.appcardapio.login.data.remote

import android.icu.util.Calendar
import com.example.appcardapio.login.model.UserAuth
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginRemoteDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): LoginRemoteDataSource {
    override suspend fun createAccount(userName: String, email: String, password: String): UserAuth {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

        // Add userName to Firestore
        firebaseFirestore.collection("usuarios")
            .add(hashMapOf(
                "nomeUsuario" to userName,
                "dataCadastro" to Calendar.getInstance().time)
            ).await()

        return mapToUserAuth(authResult)
    }

    override suspend fun signIn(email: String, password: String): UserAuth {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return mapToUserAuth(authResult)
    }

    override suspend fun recover(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override fun isSessionValid(): Boolean {
        return firebaseAuth.currentUser != null
    }

    private fun mapToUserAuth(authResult: AuthResult): UserAuth {
        authResult.user?.let { user ->
            return UserAuth(
                user.uid,
                user.displayName,
                user.email,
                user.photoUrl,
                user.phoneNumber,
                user.isEmailVerified
            )
        } ?: throw Exception("User not found")
    }
}