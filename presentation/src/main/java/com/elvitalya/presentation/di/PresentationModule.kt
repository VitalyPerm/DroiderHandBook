package com.elvitalya.presentation.di

import com.elvitalya.data.toastdispatcher.ToastDispatcherImpl
import com.elvitalya.domain.toastdispatcher.ToastDispatcher
import com.elvitalya.presentation.viewmodel.TestViewModel
import com.elvitalya.presentation.viewmodel.FavoriteViewModel
import com.elvitalya.presentation.viewmodel.AuthViewModel
import com.elvitalya.presentation.viewmodel.QuestionDetailViewModel
import com.elvitalya.presentation.viewmodel.SearchViewModel
import com.elvitalya.presentation.viewmodel.SectionsViewModel
import com.elvitalya.presentation.viewmodel.MainFlowViewModel
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