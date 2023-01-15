package com.elvitalya.droiderhandbook.data

import com.elvitalya.droiderhandbook.data.db.QuestionsDao
import com.elvitalya.droiderhandbook.data.model.FirebaseQuestion
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.utils.FireBaseHelper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.flow.flow
import com.elvitalya.droiderhandbook.utils.Result

@Singleton
class DataRepository @Inject constructor(
    private val questionsDao: QuestionsDao
) {

    private fun getErrorMessage(e: Exception) = e.message ?: "Упс, что то пошло не так"

    fun getQuestionsFlow(): Flow<List<QuestionEntity>> = questionsDao.getQuestionsFlow()

    suspend fun loadQuestions() {
        deleteAll()
        val firebaseQuestions = fetchQuestionsFromFirebase()
        firebaseQuestions.mapNotNull { it.mapToEntity() }.forEach {
            questionsDao.addQuestion(it)
        }
    }

    private suspend fun fetchQuestionsFromFirebase(): List<FirebaseQuestion> {
        return suspendCoroutine { continuation ->
            FireBaseHelper.questions
                .get()
                .addOnSuccessListener { snapShot ->
                    val response: List<FirebaseQuestion> =
                        snapShot.documents.mapNotNull { documentSnapshot -> documentSnapshot?.toObject() }
                    continuation.resume(response)
                }
                .addOnFailureListener {
                    continuation.resume(emptyList())
                }
        }
    }

    suspend fun login(
        email: String,
        pass: String
    ): Result<Unit> {
        return suspendCoroutine { continuation ->
            Firebase.auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    continuation.resume(Result.Success(Unit))
                }
                .addOnFailureListener {
                    continuation.resume(
                        Result.Error(
                            it.message ?: "Неизвестная ошибка"
                        )
                    )
                }
        }
    }

    suspend fun registration(
        email: String,
        pass: String
    ): Result<Unit> {
        return suspendCoroutine { continuation ->
            Firebase.auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    continuation.resume(Result.Success(Unit))
                }
                .addOnFailureListener {
                    continuation.resume(Result.Error(it.message ?: "Неизвестная ошибка"))
                }
        }
    }

    private suspend fun deleteAll() = questionsDao.deleteAll()

    fun getQuestionById(id: Long): Flow<Result<QuestionEntity>> =
        flow {
            emit(Result.Loading())
            try {
                val question = questionsDao.getQuestion(id)
                emit(Result.Success(question))
            } catch (e: Exception) {
                emit(Result.Error(getErrorMessage(e)))
            }
        }

    suspend fun updateQuestion(question: QuestionEntity) = flow {
        emit(Result.Loading())
        try {
            questionsDao.updateQuestion(question)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Error(getErrorMessage(e)))
        }

    }
}