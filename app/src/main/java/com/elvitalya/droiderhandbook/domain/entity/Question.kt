package com.elvitalya.droiderhandbook.domain.entity

data class Question(
    val id: String,
    val title: String,
    val text: String,
    val isFavorite: Boolean
){
    companion object {
        val EMPTY = Question(
            id = "",
            text = "",
            title = "",
            isFavorite = false
        )
    }
}
