package com.elvitalya.droiderhandbook.data.local

import com.elvitalya.droiderhandbook.data.local.dao.QuestionsDao
import com.elvitalya.droiderhandbook.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class QuestionsDaoTestImpl : QuestionsDao {
    private val data = mutableListOf<QuestionEntity>()

    override fun getAll(): Flow<List<QuestionEntity>> = flowOf(data)

    override suspend fun getQuestion(id: String): QuestionEntity =
        data.find { it.id == id } ?: QuestionEntity("", "", "")

    override suspend fun addQuestionsList(questions: List<QuestionEntity>) {
        data.addAll(questions)
    }

    override suspend fun deleteAll() {
        data.clear()
    }

    override suspend fun updateQuestion(question: QuestionEntity) {
        val oldIndex = data.indexOfFirst { it.id == question.id }
        if (oldIndex != -1) {
            data.removeAt(oldIndex)
            data.add(oldIndex, question)
        }
    }
}