package com.elvitalya.domain.usecases

import com.elvitalya.domain.repository.QuestionsRepository

class LoadAllUseCase(
    private val questionsRepository: QuestionsRepository
) {
    suspend operator fun invoke() = questionsRepository.loadAll()
}