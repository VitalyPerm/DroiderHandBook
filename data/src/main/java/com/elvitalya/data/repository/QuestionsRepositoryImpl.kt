package com.elvitalya.data.repository

import com.elvitalya.data.local.mapper.toEntity
import com.elvitalya.data.local.mapper.toQuestion
import com.elvitalya.data.local.source.LocalDataSource
import com.elvitalya.data.remote.mapper.toEntity
import com.elvitalya.data.remote.source.RemoteDataSource
import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.entity.QuestionsType
import com.elvitalya.domain.repository.QuestionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuestionsRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : QuestionsRepository {

    override fun getAll(): Flow<List<Question>> =
        localDataSource.getAll().map { it.toQuestion() }

    override suspend fun loadAll() {
        localDataSource.deleteAll()
        val javaQuestions = remoteDataSource.getJavaQuestions()
        localDataSource.addQuestionList(javaQuestions.toEntity(QuestionsType.Java))
    }

    override suspend fun updateQuestion(question: Question) =
        localDataSource.updateQuestion(question.toEntity())

    override suspend fun getById(id: String): Question =
        localDataSource.getQuestionById(id).toQuestion()

}