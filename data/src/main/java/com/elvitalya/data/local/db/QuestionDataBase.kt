package com.elvitalya.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elvitalya.data.local.dao.QuestionsDao
import com.elvitalya.data.local.entity.QuestionEntity

@Database(entities = [QuestionEntity::class], version = 1)
abstract class QuestionDataBase: RoomDatabase() {
    abstract val dao: QuestionsDao
}