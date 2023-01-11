package com.elvitalya.droiderhandbook.ui.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.sections.SectionContentItem

@Composable
fun FavoriteScreen(
    favoriteQuestions: List<QuestionEntity> = emptyList()
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(favoriteQuestions) {
            SectionContentItem(
                questionEntity = it,
                onQuestionClick = { questionId ->

                },
                onFavoriteClick = { }
            )
        }
    }
}