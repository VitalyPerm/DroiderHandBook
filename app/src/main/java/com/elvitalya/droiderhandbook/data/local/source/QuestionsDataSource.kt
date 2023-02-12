package com.elvitalya.droiderhandbook.data.local.source

import com.elvitalya.droiderhandbook.data.local.dao.QuestionsDao
import com.elvitalya.droiderhandbook.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

class QuestionsDataSource(
    private val dao: QuestionsDao
) {

    fun getAll(): Flow<List<QuestionEntity>> = dao.getAll()

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun getQuestionById(id: String): QuestionEntity = dao.getQuestion(id)

    suspend fun addQuestionList(list: List<QuestionEntity>) = dao.addQuestionsList(list)

    suspend fun updateQuestion(question: QuestionEntity) = dao.updateQuestion(question)

}