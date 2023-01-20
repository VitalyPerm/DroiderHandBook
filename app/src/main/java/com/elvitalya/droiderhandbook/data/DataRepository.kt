package com.elvitalya.droiderhandbook.data

import com.elvitalya.droiderhandbook.data.db.QuestionsDao
import com.elvitalya.droiderhandbook.data.model.FirebaseQuestion
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.utils.FireBaseHelper
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val questionsDao: QuestionsDao
) {

    fun getQuestionsFlow(): Flow<List<QuestionEntity>> = questionsDao.getQuestionsFlow()

    suspend fun loadQuestions() {
        deleteAllQuestions()
        val snapShot = FireBaseHelper.questions.get().await()
        val questions: List<FirebaseQuestion> =
            snapShot.documents.mapNotNull { documentSnapshot -> documentSnapshot?.toObject() }
        questionsDao.addQuestionsList(questions.mapNotNull { it.mapToEntity() })
    }

    suspend fun login(
        email: String,
        pass: String
    ): AuthResult = Firebase.auth.signInWithEmailAndPassword(email, pass).await()


    suspend fun registration(
        email: String,
        pass: String
    ): AuthResult = Firebase.auth.createUserWithEmailAndPassword(email, pass).await()

    private suspend fun deleteAllQuestions() = questionsDao.deleteAll()

    suspend fun getQuestionById(id: String): QuestionEntity = questionsDao.getQuestion(id)

    suspend fun updateQuestion(question: QuestionEntity) = questionsDao.updateQuestion(question)


}