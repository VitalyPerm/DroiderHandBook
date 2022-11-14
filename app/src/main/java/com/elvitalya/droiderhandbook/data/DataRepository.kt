package com.elvitalya.droiderhandbook.data

import android.util.Log
import com.elvitalya.droiderhandbook.data.db.QuestionsDao
import com.elvitalya.droiderhandbook.data.model.FirebaseQuestion
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG
import com.elvitalya.droiderhandbook.utils.FireBaseHelper
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val questionsDao: QuestionsDao
) {
    fun getQuestions(): Flow<List<QuestionEntity>> = questionsDao.getQuestionsFlow()

    fun loadQuestions() {
        Log.d(TAG, "getQuestions: loadQuestions called")
        FireBaseHelper.questions
            .get()
            .addOnSuccessListener { snapShot ->
                val response: List<FirebaseQuestion> =
                    snapShot.documents.mapNotNull { documentSnapshot -> documentSnapshot?.toObject() }
                runBlocking {
                    response.mapNotNull { it.mapToEntity() }.forEach {
                        questionsDao.addQuestion(it)
                    }
                }
            }
    }
}