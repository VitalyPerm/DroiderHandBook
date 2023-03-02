package com.elvitalya.data.remote.source

import com.elvitalya.data.remote.api.AuthApi
import com.elvitalya.data.remote.api.QuestionsApi
import com.elvitalya.data.remote.model.QuestionModel

class RemoteDataSource(
    private val questionsApi: QuestionsApi,
    private val authApi: AuthApi
) {
    suspend fun getJavaQuestions(): List<QuestionModel> =
        questionsApi.getJavaQuestions().questions

    suspend fun getKotlinQuestions(): List<QuestionModel> =
        questionsApi.getKotlinQuestions().questions

    suspend fun getAndroidQuestions(): List<QuestionModel> =
        questionsApi.getAndroidQuestions().questions

    suspend fun getCoroutineQuestions(): List<QuestionModel> =
        questionsApi.getCoroutineQuestions().questions

    suspend fun login(email: String, pass: String) = authApi.login(email, pass)

    suspend fun registration(email: String, pass: String) = authApi.registration(email, pass)

    fun isAuthorized(): Boolean = authApi.isAuthorized()

}