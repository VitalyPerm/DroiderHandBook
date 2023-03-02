package com.elvitalya.data.remote.api

import com.elvitalya.data.remote.model.ApiResponseModel
import retrofit2.http.GET

interface QuestionsApi {
    @GET("exec?action=getJava")
    suspend fun getJavaQuestions(): ApiResponseModel

    @GET("exec?action=getKotlin")
    suspend fun getKotlinQuestions(): ApiResponseModel

    @GET("exec?action=getAndroid")
    suspend fun getAndroidQuestions(): ApiResponseModel

    @GET("exec?action=getCoroutine")
    suspend fun getCoroutineQuestions(): ApiResponseModel
}