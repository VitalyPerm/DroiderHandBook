package com.elvitalya.data.remote.api

import com.elvitalya.data.remote.model.ApiResponseModel
import retrofit2.http.GET

interface QuestionsApi {
    @GET("exec?action=getJava")
    suspend fun getJavaQuestions(): ApiResponseModel
}