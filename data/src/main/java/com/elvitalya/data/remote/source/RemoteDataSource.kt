package com.elvitalya.data.remote.source

import com.elvitalya.data.remote.api.QuestionsApi
import com.elvitalya.data.remote.model.QuestionModel

class RemoteDataSource(
    private val questionsApi: QuestionsApi
) {
    suspend fun getJavaQuestions(): List<QuestionModel> = questionsApi.getJavaQuestions().questions
}