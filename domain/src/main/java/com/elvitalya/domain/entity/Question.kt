package com.elvitalya.domain.entity

data class Question(
    val number: Int,
    val title: String,
    val text: String,
    val picUrl: String,
    val isFavorite: Boolean,
    val type: QuestionsType
) {
    companion object {
        val EMPTY = Question(
            number = 0,
            text = "",
            title = "",
            picUrl = "",
            isFavorite = false,
            type = QuestionsType.Unknown
        )
    }
}
