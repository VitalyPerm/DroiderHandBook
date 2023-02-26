package com.elvitalya.data.local.mapper

import com.elvitalya.data.local.entity.QuestionEntity
import com.elvitalya.domain.entity.Question

private fun map(question: QuestionEntity) = Question(
    id = question.id,
    title = question.title,
    text = question.text,
    isFavorite = question.isFavorite
)

fun QuestionEntity.toQuestion() = map(this)

fun List<QuestionEntity>.toQuestion() = map { it.toQuestion() }

private fun map(question: Question) = QuestionEntity(
    id = question.id,
    title = question.title,
    text = question.text,
    isFavorite = question.isFavorite
)

fun Question.toEntity() = map(this)
fun List<Question>.toEntity() = map { it.toEntity() }