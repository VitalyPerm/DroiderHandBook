package com.elvitalya.data.repository

import android.util.Log
import com.elvitalya.data.local.mapper.toEntity
import com.elvitalya.data.local.mapper.toQuestion
import com.elvitalya.data.local.source.LocalDataSource
import com.elvitalya.data.remote.mapper.toEntity
import com.elvitalya.data.remote.source.RemoteDataSource
import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.entity.QuestionsType
import com.elvitalya.domain.repository.QuestionsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.suspendCoroutine

class QuestionsRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : QuestionsRepository {

    override fun getAll(): Flow<List<Question>> =
        localDataSource.getAll().map { it.toQuestion() }

    override suspend fun loadAll() {
        localDataSource.deleteAll()
        supervisorScope {
            launch {
                val javaQuestions = remoteDataSource.getJavaQuestions()
                localDataSource.addQuestionList(javaQuestions.toEntity(QuestionsType.Java))
            }

            launch {
                val kotlinQuestions = remoteDataSource.getKotlinQuestions()
                localDataSource.addQuestionList(kotlinQuestions.toEntity(QuestionsType.Kotlin))
            }

            launch {
                val androidQuestions = remoteDataSource.getAndroidQuestions()
                localDataSource.addQuestionList(androidQuestions.toEntity(QuestionsType.Android))
            }

            launch {
                val coroutineQuestions = remoteDataSource.getCoroutineQuestions()
                localDataSource.addQuestionList(coroutineQuestions.toEntity(QuestionsType.Coroutines))
            }
        }
    }

    override suspend fun updateQuestion(question: Question) =
        localDataSource.updateQuestion(question.toEntity())

    override suspend fun getById(id: Long): Question =
        localDataSource.getQuestionById(id).toQuestion()

}