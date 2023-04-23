package com.elvitalya.domain.usecases

import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.repository.QuestionsRepository

class UpdateQuestionUseCase(
    private val questionsRepository: QuestionsRepository
) {
    suspend operator fun invoke(question: Question) = questionsRepository.updateQuestion(question)
}