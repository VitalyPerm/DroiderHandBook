package com.elvitalya.data.di

import androidx.room.Room
import com.elvitalya.data.local.db.QuestionDataBase
import com.elvitalya.data.local.source.QuestionsDataSource
import com.elvitalya.data.remote.api.FireBaseApi
import com.elvitalya.data.remote.api.FireBaseApiImpl
import com.elvitalya.data.remote.source.FireBaseDataSource
import com.elvitalya.data.repository.AuthRepositoryImpl
import com.elvitalya.data.repository.QuestionsRepositoryImpl
import com.elvitalya.domain.repository.AuthRepository
import com.elvitalya.domain.repository.QuestionsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            QuestionDataBase::class.java,
            "questions_db"
        )
            .build().dao
    }
}

private val repositoryModule = module {
    singleOf(::QuestionsDataSource)
    singleOf(::FireBaseDataSource)
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<QuestionsRepository> { QuestionsRepositoryImpl(get(), get()) }
    single<FireBaseApi> { FireBaseApiImpl() }
}

val dataModules = databaseModule + repositoryModule