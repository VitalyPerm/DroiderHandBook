package com.elvitalya.data.remote.api

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthApiImpl : AuthApi {
    override suspend fun login(email: String, pass: String): AuthResult =
        Firebase.auth.signInWithEmailAndPassword(email, pass).await()

    override suspend fun registration(email: String, pass: String): AuthResult =
        Firebase.auth.createUserWithEmailAndPassword(email, pass).await()

    override suspend fun isAuthorized(): Boolean {
        return suspendCoroutine { continuation ->
            val result = Firebase.auth.currentUser != null
            continuation.resume(result)
        }
    }

}