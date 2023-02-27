package com.elvitalya.data.remote.mapper

import com.elvitalya.data.local.entity.QuestionEntity
import com.elvitalya.data.remote.model.QuestionModel
import com.elvitalya.domain.entity.QuestionsType

private fun map(question: QuestionModel, type: QuestionsType): QuestionEntity =
    QuestionEntity(
        number = question.number,
        title = question.title,
        text = question.text,
        picUrl = question.picUrl,
        isFavorite = false,
        type = type
    )

fun QuestionModel.toEntity(type: QuestionsType) = map(this, type)
fun List<QuestionModel>.toEntity(type: QuestionsType): List<QuestionEntity> =
    map { it.toEntity(type) }