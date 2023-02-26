package com.elvitalya.droiderhandbook.data.local

import com.elvitalya.data.local.dao.QuestionsDao
import com.elvitalya.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class QuestionsDaoTestImpl : com.elvitalya.data.local.dao.QuestionsDao {
    private val data = mutableListOf<com.elvitalya.data.local.entity.QuestionEntity>()

    override fun getAll(): Flow<List<com.elvitalya.data.local.entity.QuestionEntity>> = flowOf(data)

    override suspend fun getQuestion(id: String): com.elvitalya.data.local.entity.QuestionEntity =
        data.find { it.id == id } ?: com.elvitalya.data.local.entity.QuestionEntity("", "", "")

    override suspend fun addQuestionsList(questions: List<com.elvitalya.data.local.entity.QuestionEntity>) {
        data.addAll(questions)
    }

    override suspend fun deleteAll() {
        data.clear()
    }

    override suspend fun updateQuestion(question: com.elvitalya.data.local.entity.QuestionEntity) {
        val oldIndex = data.indexOfFirst { it.id == question.id }
        if (oldIndex != -1) {
            data.removeAt(oldIndex)
            data.add(oldIndex, question)
        }
    }
}