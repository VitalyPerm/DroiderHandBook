package com.elvitalya.droiderhandbook.domain.usecases

import com.elvitalya.droiderhandbook.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {

    suspend fun run(email: String, pass: String) = authRepository.login(email, pass)

}