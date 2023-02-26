package com.elvitalya.data.repository

import com.elvitalya.data.remote.source.FireBaseDataSource
import com.elvitalya.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val fireBaseDataSource: FireBaseDataSource
) : AuthRepository {
    override suspend fun login(email: String, pass: String): Result<Unit> {
        val result = fireBaseDataSource.login(email, pass)
        return if (result.user != null) Result.success(Unit)
        else Result.failure(Exception("unknown login error"))
    }


    override suspend fun registration(email: String, pass: String): Result<Unit> {
        val result = fireBaseDataSource.registration(email, pass)
        return if (result.user != null) Result.success(Unit)
        else Result.failure(Exception("unknown registration error"))
    }

}