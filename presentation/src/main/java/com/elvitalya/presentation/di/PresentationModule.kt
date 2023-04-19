package com.elvitalya.presentation.di

import com.elvitalya.data.toastdispatcher.ToastDispatcherImpl
import com.elvitalya.domain.toastdispatcher.ToastDispatcher
import com.elvitalya.presentation.core.main_activity.MainActivityViewModel
import com.elvitalya.presentation.ui.test.TestViewModel
import com.elvitalya.presentation.ui.favorite.FavoriteViewModel
import com.elvitalya.presentation.ui.authentication.auth.AuthViewModel
import com.elvitalya.presentation.ui.authentication.registration.RegistrationViewModel
import com.elvitalya.presentation.ui.question_details.QuestionDetailViewModel
import com.elvitalya.presentation.ui.search.SearchViewModel
import com.elvitalya.presentation.ui.sections.SectionsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

private val module: Module = module {
    viewModelOf(::TestViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::AuthViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::QuestionDetailViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SectionsViewModel)
    viewModelOf(::MainActivityViewModel)

    single<ToastDispatcher> { ToastDispatcherImpl(androidContext()) }

}

val presentationModule = listOf(module)