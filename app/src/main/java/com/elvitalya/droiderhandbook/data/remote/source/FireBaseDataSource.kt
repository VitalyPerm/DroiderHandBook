package com.elvitalya.droiderhandbook.data.remote.source

import com.elvitalya.droiderhandbook.data.remote.model.FirebaseQuestion
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FireBaseDataSource {

    suspend fun getAllQuestions(): List<FirebaseQuestion> = withContext(Dispatchers.IO) {
        val snapShot = Firebase.firestore.collection("questions")
            .orderBy("id", Query.Direction.ASCENDING)
            .get()
            .await()
        return@withContext snapShot.documents.mapNotNull { documentSnapshot -> documentSnapshot?.toObject() }
    }

}