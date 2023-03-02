package com.elvitalya.data.local.mapper

import com.elvitalya.data.local.entity.QuestionEntity
import com.elvitalya.domain.entity.Question

private fun map(question: QuestionEntity) = Question(
    id = question.id,
    number = question.number,
    title = question.title,
    text = question.text,
    picUrl = question.picUrl,
    isFavorite = question.isFavorite,
    type = question.type
)

fun QuestionEntity.toQuestion() = map(this)

fun List<QuestionEntity>.toQuestion() = map { it.toQuestion() }

private fun map(question: Question) = QuestionEntity(
    id = question.id,
    number = question.number,
    title = question.title,
    text = question.text,
    picUrl = question.picUrl,
    isFavorite = question.isFavorite
)

fun Question.toEntity() = map(this)