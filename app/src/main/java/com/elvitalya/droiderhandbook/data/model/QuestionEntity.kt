package com.elvitalya.droiderhandbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val text: String,
    val favorite: Boolean = false
) {
    companion object {
        val EMPTY = QuestionEntity(
            id = 0,
            text = "",
            title = "",
            favorite = false
        )
    }
}
