package com.elvitalya.droiderhandbook.di


import com.elvitalya.domain.usecases.GetAllUseCase
import com.elvitalya.domain.usecases.GetByIdUseCase
import com.elvitalya.domain.usecases.LoadAllUseCase
import com.elvitalya.domain.usecases.UpdateQuestionUseCase
import com.elvitalya.domain.usecases.LoginUseCase
import com.elvitalya.domain.usecases.RegistrationUseCase
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