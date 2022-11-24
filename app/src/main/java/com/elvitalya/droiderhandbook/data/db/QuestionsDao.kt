package com.elvitalya.droiderhandbook.data.db

import androidx.room.*
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsDao {

    @Query("SELECT * FROM questionentity")
    fun getQuestionsFlow(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questionentity WHERE id=(:id)")
    suspend fun getQuestion(id: Int): QuestionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestion(question: QuestionEntity)

    @Delete
    suspend fun deleteQuestion(question: QuestionEntity)

    @Query("DELETE FROM questionentity")
    suspend fun deleteAll()

    @Update
    suspend fun updateQuestion(question: QuestionEntity)
}