package com.elvitalya.data.repository

import com.elvitalya.domain.repository.AuthRepository

class AuthRepositoryImpl(
) : AuthRepository {
    override suspend fun login(email: String, pass: String): Result<Unit> = Result.success(Unit)


    override suspend fun registration(email: String, pass: String): Result<Unit> =
        Result.success(Unit)

}