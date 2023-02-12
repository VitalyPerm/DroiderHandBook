package com.elvitalya.droiderhandbook.data.di

import androidx.room.Room
import com.elvitalya.droiderhandbook.data.local.db.QuestionDataBase
import com.elvitalya.droiderhandbook.data.local.source.QuestionsDataSource
import com.elvitalya.droiderhandbook.data.remote.source.FireBaseDataSource
import com.elvitalya.droiderhandbook.data.repository.AuthRepositoryImpl
import com.elvitalya.droiderhandbook.data.repository.QuestionsRepositoryImpl
import com.elvitalya.droiderhandbook.domain.repository.QuestionsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private val databaseModule = module {
    single {
        Room.databaseBuilder(get(), QuestionDataBase::class.java, "questions_db")
            .build().dao
    }
}

private val repositoryModule = module {
    singleOf(::QuestionsDataSource)
    singleOf(::FireBaseDataSource)
    singleOf(::AuthRepositoryImpl)
    single<QuestionsRepository> { QuestionsRepositoryImpl(get(), get()) }
}

val dataModules = databaseModule + repositoryModule