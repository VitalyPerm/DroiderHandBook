package com.elvitalya.data.di

import androidx.room.Room
import com.elvitalya.data.local.db.QuestionDataBase
import com.elvitalya.data.local.source.LocalDataSource
import com.elvitalya.data.remote.api.AuthApi
import com.elvitalya.data.remote.api.AuthApiImpl
import com.elvitalya.data.remote.api.QuestionsApi
import com.elvitalya.data.remote.source.RemoteDataSource
import com.elvitalya.data.repository.AuthRepositoryImpl
import com.elvitalya.data.repository.QuestionsRepositoryImpl
import com.elvitalya.data.toastdispatcher.ToastDispatcherImpl
import com.elvitalya.domain.repository.AuthRepository
import com.elvitalya.domain.repository.QuestionsRepository
import com.elvitalya.domain.toastdispatcher.ToastDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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

private val networkModule = module {
    val timeOut = 20_000L
    val baseUrl =
        "https://script.google.com/macros/s/AKfycbw2HqeDRjPpA3XdtEYpeiQ_oMr_I-iupPaAQR7to9wEiWZZk_uzhK5-RxxEBbh5j-Fmeg/"

    single {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(timeOut, TimeUnit.MILLISECONDS)
            .readTimeout(timeOut, TimeUnit.MILLISECONDS)
            .writeTimeout(timeOut, TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor)
            .build()

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
            .create(QuestionsApi::class.java)
    }


}

private val repositoryModule = module {
    singleOf(::LocalDataSource)
    singleOf(::RemoteDataSource)
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::QuestionsRepositoryImpl) { bind<QuestionsRepository>() }
    singleOf(::AuthApiImpl) { bind<AuthApi>() }
    singleOf(::ToastDispatcherImpl) { bind<ToastDispatcher>() }
}

val dataModules = databaseModule + repositoryModule + networkModule