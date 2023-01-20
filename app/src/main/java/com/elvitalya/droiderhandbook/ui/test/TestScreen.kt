package com.elvitalya.droiderhandbook.ui.test

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.droiderhandbook.ui.core.rippleClickable
import com.elvitalya.droiderhandbook.ui.theme.accent
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.theme.black
import com.elvitalya.droiderhandbook.ui.theme.white
import kotlinx.coroutines.delay

@Composable
fun TestScreen(
    question: QuestionEntity,
    onNextQuestionClick: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        delay(1000)
        onNextQuestionClick()
    }

    var questionExpanded by remember { mutableStateOf(false) }
    val text = if (questionExpanded) question.text else question.title

    ProvideWindowInsets {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(6.dp, shape = RoundedCornerShape(16.dp), clip = true)
                    .background(white, RoundedCornerShape(16.dp))
                    .clickable { questionExpanded = questionExpanded.not() }
                    .animateContentSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = black,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(accent, RoundedCornerShape(16.dp))
                    .rippleClickable({
                        questionExpanded = false
                        onNextQuestionClick()
                    }),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.next_question),
                    modifier = Modifier
                        .padding(16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = white
                )
            }
        }
    }
}