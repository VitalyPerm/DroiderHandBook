package com.elvitalya.domain.usecases

import com.elvitalya.domain.repository.AuthRepository

class RegistrationUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun run(email: String, pass: String) = authRepository.registration(email, pass)

}