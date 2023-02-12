package com.elvitalya.droiderhandbook.domain.usecases

import com.elvitalya.droiderhandbook.domain.entity.Question
import com.elvitalya.droiderhandbook.domain.repository.QuestionsRepository

class UpdateQuestionUseCase(
    private val questionsRepository: QuestionsRepository
) {

    suspend fun run(question: Question) = questionsRepository.updateQuestion(question)

}