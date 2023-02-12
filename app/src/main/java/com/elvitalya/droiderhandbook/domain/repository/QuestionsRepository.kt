package com.elvitalya.droiderhandbook.domain.repository

import com.elvitalya.droiderhandbook.domain.entity.Question
import kotlinx.coroutines.flow.Flow

interface QuestionsRepository {

    fun getAll(): Flow<List<Question>>

    suspend fun loadAll()

    suspend fun updateQuestion(question: Question)

    suspend fun getById(id: String): Question

}