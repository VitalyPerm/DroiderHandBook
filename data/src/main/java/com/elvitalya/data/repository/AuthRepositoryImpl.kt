package com.elvitalya.data.repository

import com.elvitalya.data.remote.source.RemoteDataSource
import com.elvitalya.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : AuthRepository {
    override suspend fun login(email: String, pass: String): Result<Unit> {
        val result = remoteDataSource.login(email, pass)
        return if (result.user != null) Result.success(Unit)
        else Result.failure(Exception("Some error occurred"))
    }


    override suspend fun registration(email: String, pass: String): Result<Unit> {
        val result = remoteDataSource.registration(email, pass)
        return if (result.user != null) Result.success(Unit)
        else Result.failure(Exception("Some error occurred"))
    }

    override suspend fun isAuthorized(): Boolean = remoteDataSource.isAuthorized()

}