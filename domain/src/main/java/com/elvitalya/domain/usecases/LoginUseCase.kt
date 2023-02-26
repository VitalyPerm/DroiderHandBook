package com.elvitalya.domain.usecases

import com.elvitalya.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun run(email: String, pass: String) = authRepository.login(email, pass)

}