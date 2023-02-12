package com.elvitalya.droiderhandbook.domain.usecases

import com.elvitalya.droiderhandbook.domain.entity.Question
import com.elvitalya.droiderhandbook.domain.repository.QuestionsRepository
import kotlinx.coroutines.flow.Flow

class GetAllUseCase(
    private val questionsRepository: QuestionsRepository
) {

    fun run(): Flow<List<Question>> = questionsRepository.getAll()

}