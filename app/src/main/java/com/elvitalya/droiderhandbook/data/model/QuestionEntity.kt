package com.elvitalya.droiderhandbook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val text: String,
    val favorite: Boolean = false
)
