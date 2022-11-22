package com.elvitalya.droiderhandbook.utils

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FireBaseHelper {
    val questions = Firebase.firestore.collection("questions")

    val javaQuestionsIds = "1"
    val kotlinQuestionsIds = "2"
    val androidQuestionsIds = "3"
    val basicQuestionsIds = "4"
}