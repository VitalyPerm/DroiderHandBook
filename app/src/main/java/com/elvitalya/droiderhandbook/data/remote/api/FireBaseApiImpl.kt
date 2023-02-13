package com.elvitalya.droiderhandbook.data.remote.api

import com.elvitalya.droiderhandbook.data.remote.model.FirebaseQuestion
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class FireBaseApiImpl : FireBaseApi {
    override suspend fun getAllQuestions(): List<FirebaseQuestion> = withContext(Dispatchers.IO) {
        val snapShot = Firebase.firestore.collection("questions")
            .orderBy("id", Query.Direction.ASCENDING)
            .get()
            .await()
        return@withContext snapShot.documents.mapNotNull { documentSnapshot -> documentSnapshot?.toObject() }
    }
}