package com.elvitalya.domain.repository

interface AuthRepository {

    suspend fun login(email: String, pass: String): Result<Unit>

    suspend fun registration(email: String, pass: String): Result<Unit>

    fun isAuthorized(): Boolean

}