package com.elvitalya.droiderhandbook.ui.questiondetail

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.core.ErrorBanner
import com.elvitalya.droiderhandbook.ui.core.LoadingBanner
import com.elvitalya.droiderhandbook.utils.ViewState

@Composable
fun QuestionDetailsScreen(
    question: QuestionEntity?,
    viewState: ViewState
) {

    Crossfade(
        targetState = viewState,
        modifier = Modifier
            .fillMaxSize()
    ) { viewState ->
        when (viewState) {
            ViewState.Content -> Content(question)
            ViewState.Error -> ErrorBanner()
            ViewState.Loading -> LoadingBanner()
        }
    }


}

@Composable
private fun Content(
    question: QuestionEntity?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {

        Text(
            text = question?.title ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = question?.text ?: "",
            modifier = Modifier
                .padding(8.dp)
        )

    }
}