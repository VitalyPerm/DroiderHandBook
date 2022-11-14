package com.elvitalya.droiderhandbook.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elvitalya.droiderhandbook.data.model.QuestionEntity

@Database(entities = [QuestionEntity::class], version = 1)
abstract class QuestionDataBase: RoomDatabase() {
    abstract val dao: QuestionsDao
}