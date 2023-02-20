package com.elvitalya.droiderhandbook.data.remote.api

import com.elvitalya.droiderhandbook.data.remote.model.FirebaseQuestion
import com.google.firebase.auth.AuthResult

interface FireBaseApi {

    suspend fun getAllQuestions(): List<FirebaseQuestion>

    suspend fun login(email: String, pass: String): AuthResult

    suspend fun registration(email: String, pass: String): AuthResult

}