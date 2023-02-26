package com.elvitalya.domain.usecases

import com.elvitalya.domain.repository.QuestionsRepository

class LoadAllUseCase(
    private val questionsRepository: QuestionsRepository
) {

    suspend fun run() = questionsRepository.loadAll()

}