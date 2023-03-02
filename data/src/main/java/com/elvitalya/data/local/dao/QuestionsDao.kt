package com.elvitalya.data.local.dao

import androidx.room.*
import com.elvitalya.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsDao {

    @Query("SELECT * FROM questionentity")
    fun getAll(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questionentity WHERE id=(:id)")
    suspend fun getQuestion(id: Long): QuestionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestionsList(questions: List<QuestionEntity>)

    @Query("DELETE FROM questionentity")
    suspend fun deleteAll()

    @Update
    suspend fun updateQuestion(question: QuestionEntity)
}