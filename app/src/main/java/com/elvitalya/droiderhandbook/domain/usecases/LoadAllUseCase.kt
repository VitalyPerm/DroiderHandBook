package com.elvitalya.droiderhandbook.domain.usecases

import com.elvitalya.droiderhandbook.domain.repository.QuestionsRepository

class LoadAllUseCase(
    private val questionsRepository: QuestionsRepository
) {

    suspend fun run() = questionsRepository.loadAll()

}