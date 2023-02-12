package com.elvitalya.droiderhandbook.data.repository

import com.elvitalya.droiderhandbook.data.local.mapper.toEntity
import com.elvitalya.droiderhandbook.data.local.mapper.toQuestion
import com.elvitalya.droiderhandbook.data.local.source.QuestionsDataSource
import com.elvitalya.droiderhandbook.data.remote.mapper.toEntity
import com.elvitalya.droiderhandbook.data.remote.source.FireBaseDataSource
import com.elvitalya.droiderhandbook.domain.entity.Question
import com.elvitalya.droiderhandbook.domain.repository.QuestionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuestionsRepositoryImpl(
    private val questionsDataSource: QuestionsDataSource,
    private val fireBaseDataSource: FireBaseDataSource
) : QuestionsRepository {

    override fun getAll(): Flow<List<Question>> =
        questionsDataSource.getAll().map { it.toQuestion() }

    override suspend fun loadAll() {
        questionsDataSource.deleteAll()
        val questionsList = fireBaseDataSource.getAllQuestions()
        questionsDataSource.addQuestionList(questionsList.toEntity())
    }

    override suspend fun updateQuestion(question: Question) =
        questionsDataSource.updateQuestion(question.toEntity())

    override suspend fun getById(id: String): Question =
        questionsDataSource.getQuestionById(id).toQuestion()

}