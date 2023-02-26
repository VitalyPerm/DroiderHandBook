package com.elvitalya.domain.usecases

import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.repository.QuestionsRepository
import kotlinx.coroutines.flow.Flow

class GetAllUseCase(
    private val questionsRepository: QuestionsRepository
) {

    fun run(): Flow<List<Question>> = questionsRepository.getAll()

}