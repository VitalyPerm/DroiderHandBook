package com.elvitalya.presentation.ui.question_details

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elvitalya.domain.entity.Question
import com.elvitalya.presentation.core.AppBar
import com.elvitalya.presentation.core.EmptyBanner
import com.elvitalya.presentation.core.ErrorBanner
import com.elvitalya.presentation.core.LoadingBanner
import com.elvitalya.presentation.core.ViewState
import com.elvitalya.presentation.theme.white
import org.koin.androidx.compose.koinViewModel


@Composable
fun QuestionDetailsScreen(
    viewModel: QuestionDetailViewModel = koinViewModel(),
    questionId: String?,
    onCloseClick: () -> Unit
) {

    LaunchedEffect(key1 = Unit, block = { viewModel.getQuestionById(questionId) })

    Screen(
        question = viewModel.question,
        viewState = viewModel.viewState,
        onCloseClick = onCloseClick
    )
}

@Composable
private fun Screen(
    question: Question?,
    viewState: ViewState,
    onCloseClick: () -> Unit
) {

    Crossfade(
        targetState = viewState,
        modifier = Modifier
            .fillMaxSize(), label = ""
    ) { state ->
        when (state) {
            ViewState.Content -> Content(
                question = question,
                onCloseClick = onCloseClick
            )

            ViewState.Error -> ErrorBanner()
            ViewState.Loading -> LoadingBanner()
            ViewState.Empty -> EmptyBanner()
        }
    }
}

@Composable
private fun Content(
    question: Question?,
    onCloseClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        AppBar(
            title = question?.title ?: "",
            onIconClick = onCloseClick,
            icon = Icons.Default.Close
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SelectionContainer(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = question?.text ?: "",
                    lineHeight = 24.sp,
                    fontSize = 20.sp
                )
            }

        }

    }
}