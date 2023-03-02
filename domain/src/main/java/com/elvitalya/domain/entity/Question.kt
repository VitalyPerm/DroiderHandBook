package com.elvitalya.domain.entity

data class Question(
    val id: Long,
    val number: Int,
    val title: String,
    val text: String,
    val picUrl: String,
    val isFavorite: Boolean,
    val type: QuestionsType
) {
    companion object {
        val EMPTY = Question(
            id = 0L,
            number = 0,
            text = "",
            title = "",
            picUrl = "",
            isFavorite = false,
            type = QuestionsType.Unknown
        )
    }
}
