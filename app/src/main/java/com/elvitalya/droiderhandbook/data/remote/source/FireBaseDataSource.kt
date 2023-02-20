package com.elvitalya.droiderhandbook.data.remote.source

import com.elvitalya.droiderhandbook.data.remote.api.FireBaseApi
import com.elvitalya.droiderhandbook.data.remote.model.FirebaseQuestion
import com.google.firebase.auth.AuthResult

class FireBaseDataSource(
    private val api: FireBaseApi
) {

    suspend fun getAllQuestions(): List<FirebaseQuestion> = api.getAllQuestions()

    suspend fun login(email: String, pass: String): AuthResult = api.login(email, pass)

    suspend fun registration(email: String, pass: String): AuthResult =
        api.registration(email, pass)
}