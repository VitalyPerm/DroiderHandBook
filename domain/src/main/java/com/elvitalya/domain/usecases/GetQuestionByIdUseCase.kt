package com.elvitalya.domain.usecases

import com.elvitalya.domain.repository.QuestionsRepository

class GetQuestionByIdUseCase(
    private val questionsRepository: QuestionsRepository
) {
    suspend operator fun invoke(id: Long) = questionsRepository.getById(id)
}