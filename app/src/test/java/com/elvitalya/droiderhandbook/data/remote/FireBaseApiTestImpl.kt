package com.elvitalya.droiderhandbook.data.remote

import com.elvitalya.data.remote.api.FireBaseApi


//class FireBaseApiTestImpl : FireBaseApi {
//
//    companion object {
//        fun getFakeList(): List<com.elvitalya.data.remote.model.FirebaseQuestion> {
//            val list = mutableListOf<com.elvitalya.data.remote.model.FirebaseQuestion>()
//            repeat(3) {
//                list.add(
//                    com.elvitalya.data.remote.model.FirebaseQuestion(
//                        it.toString(),
//                        it.toString(),
//                        it.toString()
//                    )
//                )
//            }
//            return list
//        }
//    }
//
//    override suspend fun getAllQuestions(): List<com.elvitalya.data.remote.model.FirebaseQuestion> = getFakeList()
//
//    override suspend fun login(email: String, pass: String): AuthResult =
//        FireBaseAuthResultTestImpl(email, pass)
//
//    override suspend fun registration(email: String, pass: String): AuthResult =
//        FireBaseAuthResultTestImpl(email, pass)
//
//}