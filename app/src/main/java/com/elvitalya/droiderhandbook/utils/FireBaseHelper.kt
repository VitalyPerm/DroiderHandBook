package com.elvitalya.droiderhandbook.utils

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FireBaseHelper {
    val mainSection = Firebase.firestore.collection("mainSections")
    val questions = Firebase.firestore.collection("questions")

    val javaQuestionsIds = listOf(2,3)
    val kotlinQuestionsIds = listOf(0,1)
    val androidQuestionsIds = listOf(4,5)
    val basicQuestionsIds = listOf(6,7,8,9,10)
}