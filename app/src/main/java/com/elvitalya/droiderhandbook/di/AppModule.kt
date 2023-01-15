package com.elvitalya.droiderhandbook.di

import android.app.Application
import androidx.room.Room
import com.elvitalya.droiderhandbook.data.db.QuestionDataBase
import com.elvitalya.droiderhandbook.data.db.QuestionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuestionsDao(app: Application): QuestionsDao {
        return Room.databaseBuilder(
            app,
            QuestionDataBase::class.java,
            "questions_db"
        )
            .fallbackToDestructiveMigration()
            .build().dao
    }
}