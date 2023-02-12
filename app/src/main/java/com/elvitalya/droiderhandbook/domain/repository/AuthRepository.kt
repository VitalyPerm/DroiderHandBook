package com.elvitalya.droiderhandbook.domain.repository

import com.google.firebase.auth.AuthResult

interface AuthRepository {

    suspend fun login(email: String, pass: String): AuthResult

    suspend fun registration(email: String, pass: String): AuthResult

}