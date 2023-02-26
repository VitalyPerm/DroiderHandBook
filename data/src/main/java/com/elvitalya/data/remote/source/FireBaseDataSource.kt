package com.elvitalya.data.remote.source

import com.elvitalya.data.remote.api.FireBaseApi
import com.elvitalya.data.remote.model.FirebaseQuestion
import com.google.firebase.auth.AuthResult

class FireBaseDataSource(
    private val api: FireBaseApi
) {

    suspend fun getAllQuestions(): List<FirebaseQuestion> = api.getAllQuestions()

    suspend fun login(email: String, pass: String): AuthResult = api.login(email, pass)

    suspend fun registration(email: String, pass: String): AuthResult =
        api.registration(email, pass)
}