package com.elvitalya.droiderhandbook.utils

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FireBaseHelper {
    val questions = Firebase.firestore.collection("questions")

    val javaQuestionsIds = 1000..19999
    val kotlinQuestionsIds = 2000..2999
    val androidQuestionsIds = 3000..3999
    val basicQuestionsIds = 4000..4999
}