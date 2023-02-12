package com.elvitalya.droiderhandbook.presentation.di

import com.elvitalya.droiderhandbook.domain.toastdispatcher.ToastDispatcher
import com.elvitalya.droiderhandbook.presentation.toastdispatcher.ToastDispatcherImpl
import com.elvitalya.droiderhandbook.presentation.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

private val module: Module = module {
    viewModelOf(::TestViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::QuestionDetailViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SectionsViewModel)
    viewModelOf(::MainFlowViewModel)

    single<ToastDispatcher> { ToastDispatcherImpl(androidContext()) }

}

val presentationModule = listOf(module)