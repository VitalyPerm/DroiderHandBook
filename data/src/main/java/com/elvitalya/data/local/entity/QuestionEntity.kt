package com.elvitalya.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elvitalya.domain.entity.QuestionsType

@Entity
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val number: Int,
    val title: String,
    val text: String,
    val picUrl: String,
    val isFavorite: Boolean = false,
    val type: QuestionsType = QuestionsType.Unknown
)
