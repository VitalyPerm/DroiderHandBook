package com.elvitalya.domain.usecases

import com.elvitalya.domain.repository.QuestionsRepository

class GetByIdUseCase(
    private val questionsRepository: QuestionsRepository
) {

    suspend fun run(id: Long) = questionsRepository.getById(id)

}