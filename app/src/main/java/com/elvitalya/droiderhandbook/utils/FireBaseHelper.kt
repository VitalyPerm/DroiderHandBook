package com.elvitalya.droiderhandbook.utils

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FireBaseHelper {
    val mainSection = Firebase.firestore.collection("mainSections")
}