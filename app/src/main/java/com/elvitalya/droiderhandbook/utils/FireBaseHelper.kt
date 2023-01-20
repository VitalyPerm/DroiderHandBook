package com.elvitalya.droiderhandbook.utils

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FireBaseHelper {
    val questions = Firebase.firestore.collection("questions")
        .orderBy("id", Query.Direction.ASCENDING)

    const val javaQuestionsPrefix = "a"
    const val kotlinQuestionsPrefix = "b"
    const val androidQuestionsPrefix = "c"
    const val basicQuestionsPrefix = "d"
}