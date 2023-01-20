package com.elvitalya.droiderhandbook.utils

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FireBaseHelper {
    val questions = Firebase.firestore.collection("questions")

    const val javaQuestionsPrefix = "a"
    const val kotlinQuestionsPrefix = "b"
    const val androidQuestionsPrefix = "c"
    const val basicQuestionsPrefix = "d"
}