package com.elvitalya.droiderhandbook.domain.usecases

import com.elvitalya.droiderhandbook.domain.repository.QuestionsRepository

class GetByIdUseCase(
    private val questionsRepository: QuestionsRepository
) {

    suspend fun run(id: String) = questionsRepository.getById(id)

}