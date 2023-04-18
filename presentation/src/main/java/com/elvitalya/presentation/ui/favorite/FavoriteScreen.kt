package com.elvitalya.presentation.ui.favorite

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elvitalya.domain.entity.Question
import com.elvitalya.presentation.core.EmptyBanner
import com.elvitalya.presentation.core.ErrorBanner
import com.elvitalya.presentation.core.LoadingBanner
import com.elvitalya.presentation.core.ViewState
import com.elvitalya.presentation.ui.sections.SectionContentItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = koinViewModel(),
    onQuestionClick: (Long) -> Unit
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val questions by viewModel.questions.collectAsStateWithLifecycle(
        initialValue = emptyList(),
        lifecycle = lifecycle
    )

    Screen(
        questions = questions,
        onFavoriteClick = viewModel::onFavoriteClick,
        onQuestionClick = onQuestionClick,
        viewState = viewModel.viewState
    )
}

@Composable
private fun Screen(
    questions: List<Question>,
    onFavoriteClick: (Question) -> Unit,
    onQuestionClick: (Long) -> Unit,
    viewState: ViewState
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(targetState = viewState, label = "") { viewState ->
            when (viewState) {
                ViewState.Content -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp)
                    ) {
                        items(questions) {
                            SectionContentItem(
                                question = it,
                                onQuestionClick = onQuestionClick,
                                onFavoriteClick = onFavoriteClick
                            )
                        }
                    }
                }

                ViewState.Error -> ErrorBanner()
                ViewState.Loading -> LoadingBanner()
                ViewState.Empty -> EmptyBanner()
            }
        }
    }


}