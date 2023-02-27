package com.elvitalya.data.remote.api

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthApiImpl : AuthApi {
    override suspend fun login(email: String, pass: String): AuthResult =
        Firebase.auth.signInWithEmailAndPassword(email, pass).await()

    override suspend fun registration(email: String, pass: String): AuthResult =
        Firebase.auth.createUserWithEmailAndPassword(email, pass).await()

    override fun isAuthorized(): Boolean = Firebase.auth.currentUser != null

}