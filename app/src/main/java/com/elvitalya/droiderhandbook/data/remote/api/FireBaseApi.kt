package com.elvitalya.droiderhandbook.data.remote.api

import com.elvitalya.droiderhandbook.data.remote.model.FirebaseQuestion

interface FireBaseApi {

    suspend fun getAllQuestions(): List<FirebaseQuestion>

}