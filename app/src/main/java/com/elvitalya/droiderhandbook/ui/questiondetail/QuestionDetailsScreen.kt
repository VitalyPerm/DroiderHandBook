package com.elvitalya.droiderhandbook.ui.questiondetail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.core.AppBar
import com.elvitalya.droiderhandbook.ui.core.EmptyBanner
import com.elvitalya.droiderhandbook.ui.core.ErrorBanner
import com.elvitalya.droiderhandbook.ui.core.LoadingBanner
import com.elvitalya.droiderhandbook.ui.theme.white
import com.elvitalya.droiderhandbook.utils.ViewState
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun QuestionDetailsScreen(
    question: QuestionEntity?,
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
    question: QuestionEntity?,
    onCloseClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        AppBar(
            title = question?.title ?: "",
            onCloseClick = onCloseClick
        )

        Text(
            text = question?.text ?: "",
            modifier = Modifier
                .padding(8.dp)
        )

    }
}