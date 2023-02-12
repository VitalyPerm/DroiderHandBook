package com.elvitalya.droiderhandbook.domain.di

import com.elvitalya.droiderhandbook.domain.usecases.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private val modules = module {
    singleOf(::GetByIdUseCase)
    singleOf(::GetAllUseCase)
    singleOf(::LoadAllUseCase)
    singleOf(::UpdateQuestionUseCase)
    singleOf(::LoginUseCase)
    singleOf(::RegistrationUseCase)
}

val domainModules = listOf(modules)