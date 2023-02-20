package com.elvitalya.droiderhandbook.data.repository

import com.elvitalya.droiderhandbook.data.remote.source.FireBaseDataSource
import com.elvitalya.droiderhandbook.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult

class AuthRepositoryImpl(
    private val fireBaseDataSource: FireBaseDataSource
) : AuthRepository {
    override suspend fun login(email: String, pass: String): AuthResult =
        fireBaseDataSource.login(email, pass)

    override suspend fun registration(email: String, pass: String): AuthResult =
        fireBaseDataSource.registration(email, pass)
}