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

    @Update
    suspend fun updateQuestion(note: QuestionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestion(note: QuestionEntity)

    @Delete
    suspend fun deleteQuestion(note: QuestionEntity)

    @Query("DELETE FROM questionentity")
    suspend fun deleteAll()
}