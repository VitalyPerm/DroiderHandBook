package com.elvitalya.presentation.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.domain.entity.Question
import com.elvitalya.presentation.theme.white
import com.elvitalya.presentation.core.*
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun QuestionDetailsScreen(
    question: Question?,
    viewState: ViewState,
    onCloseClick: () -> Unit
) {

    ProvideWindowInsets {
        Crossfade(
            targetState = viewState,
            modifier = Modifier
                .fillMaxSize()
        ) { viewState ->
            when (viewState) {
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