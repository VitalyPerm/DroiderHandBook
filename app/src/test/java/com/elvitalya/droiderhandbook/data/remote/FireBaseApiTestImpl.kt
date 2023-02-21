package com.elvitalya.droiderhandbook.data.remote

import com.elvitalya.droiderhandbook.data.remote.api.FireBaseApi
import com.elvitalya.droiderhandbook.data.remote.model.FirebaseQuestion
import com.google.firebase.auth.AuthResult

class FireBaseApiTestImpl : FireBaseApi {

    companion object {
        fun getFakeList(): List<FirebaseQuestion> {
            val list = mutableListOf<FirebaseQuestion>()
            repeat(3) {
                list.add(FirebaseQuestion(it.toString(), it.toString(), it.toString()))
            }
            return list
        }
    }

    override suspend fun getAllQuestions(): List<FirebaseQuestion> = getFakeList()

    override suspend fun login(email: String, pass: String): AuthResult =
        FireBaseAuthResultTestImpl(email, pass)

    override suspend fun registration(email: String, pass: String): AuthResult =
        FireBaseAuthResultTestImpl(email, pass)

}