package com.elvitalya.domain.usecases

import com.elvitalya.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, pass: String) = authRepository.login(email, pass)

}