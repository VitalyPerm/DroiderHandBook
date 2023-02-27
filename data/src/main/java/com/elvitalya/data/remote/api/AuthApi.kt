package com.elvitalya.data.remote.api

import com.google.firebase.auth.AuthResult

interface AuthApi {
    suspend fun login(email: String, pass: String): AuthResult

    suspend fun registration(email: String, pass: String): AuthResult

    fun isAuthorized(): Boolean
}