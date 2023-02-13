package com.elvitalya.droiderhandbook.data.remote.source

import com.elvitalya.droiderhandbook.data.remote.api.FireBaseApi
import com.elvitalya.droiderhandbook.data.remote.model.FirebaseQuestion

class FireBaseDataSource(
    private val api: FireBaseApi
) {

    suspend fun getAllQuestions(): List<FirebaseQuestion> = api.getAllQuestions()

}