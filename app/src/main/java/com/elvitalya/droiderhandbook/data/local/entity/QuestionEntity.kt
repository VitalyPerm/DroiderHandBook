package com.elvitalya.droiderhandbook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionEntity(
    @PrimaryKey val id: String,
    val title: String,
    val text: String,
    val isFavorite: Boolean = false
)
