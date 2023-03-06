package com.elvitalya.droiderhandbook.data.repository

import androidx.annotation.VisibleForTesting
import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.repository.QuestionsRepository
import kotlinx.coroutines.flow.*

class FakeQuestionsRepository : QuestionsRepository {

    private val _savedQuestions = MutableStateFlow<List<Question>>(emptyList())
    val savedQuestions: StateFlow<List<Question>> = _savedQuestions.asStateFlow()

    override fun getAll(): Flow<List<Question>> = savedQuestions

    override suspend fun loadAll() {}

    override suspend fun updateQuestion(question: Question) {
        val questions = savedQuestions.value.toMutableList()
        val questionToDelete = questions.find { it.id == question.id }
        questions.remove(questionToDelete)
        questions.add(question)
        _savedQuestions.update { questions }
    }

    override suspend fun getById(id: Long): Question {
        val q = savedQuestions.value.find { it.id == id }
        return q!!
    }

    @VisibleForTesting
    fun addQuestions(vararg questions: Question) {
        _savedQuestions.update {
            val questionsList = mutableListOf<Question>()
            for (question in questions) {
                questionsList.add(question)
            }
            questionsList
        }
    }

    @VisibleForTesting
    fun clearQuestionsList() {
        _savedQuestions.update { emptyList() }
    }
}