package com.elvitalya.droiderhandbook.ui.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class GlobalViewModel : ViewModel() {




    fun loadData() {
        val docRef = Firebase.firestore.collection("sectionsList")
        docRef.get()
            .addOnSuccessListener { documents ->
//                list.clear()
//                list.addAll(documents.map { it.toObject() })
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun getByTitle(title: String) {
        val docRef =
            Firebase.firestore.collection(title).orderBy("order", Query.Direction.ASCENDING)
        docRef.get()
            .addOnSuccessListener { documents ->
//                detailList.clear()
//                detailList.addAll(documents.map { it.toObject() })
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}