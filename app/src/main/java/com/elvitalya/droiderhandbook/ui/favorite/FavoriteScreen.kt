package com.elvitalya.droiderhandbook.ui.favorite

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.core.ErrorBanner
import com.elvitalya.droiderhandbook.ui.core.LoadingBanner
import com.elvitalya.droiderhandbook.ui.sections.SectionContentItem
import com.elvitalya.droiderhandbook.utils.ViewState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun FavoriteScreen(
    questions: List<QuestionEntity>,
    onFavoriteClick: (QuestionEntity) -> Unit,
    onQuestionClick: (Long) -> Unit,
    viewState: ViewState
) {

    ProvideWindowInsets {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            Crossfade(targetState = viewState) { viewState ->
                when (viewState) {
                    ViewState.Content -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp)
                        ) {
                            items(questions) {
                                SectionContentItem(
                                    questionEntity = it,
                                    onQuestionClick = onQuestionClick,
                                    onFavoriteClick = onFavoriteClick
                                )
                            }
                        }
                    }

                    ViewState.Error -> ErrorBanner()
                    ViewState.Loading -> LoadingBanner()
                }
            }
        }
    }


}