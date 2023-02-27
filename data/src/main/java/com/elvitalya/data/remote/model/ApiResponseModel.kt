package com.elvitalya.data.remote.model

import com.google.gson.annotations.SerializedName


data class ApiResponseModel(
    @SerializedName("items") val questions: List<QuestionModel>
)
