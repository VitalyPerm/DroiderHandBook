package com.elvitalya.droiderhandbook.data.model


data class FirebaseQuestion(
    val id: Long? = null,
    val title: String? = null,
    val text: String? = null
) {
    fun mapToEntity(): QuestionEntity? =
        if (id == null || title == null || text == null) null
        else QuestionEntity(id = id, title = title, text = text)
}