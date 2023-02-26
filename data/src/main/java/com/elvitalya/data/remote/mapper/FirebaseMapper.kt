package com.elvitalya.data.remote.mapper

import com.elvitalya.data.local.entity.QuestionEntity
import com.elvitalya.data.remote.model.FirebaseQuestion

private fun map(question: FirebaseQuestion): QuestionEntity? =
    if (question.id == null || question.title == null || question.text == null) null
    else QuestionEntity(id = question.id, title = question.title, text = question.text)

fun FirebaseQuestion.toEntity() = map(this)
fun List<FirebaseQuestion>.toEntity(): List<QuestionEntity> = mapNotNull { it.toEntity() }