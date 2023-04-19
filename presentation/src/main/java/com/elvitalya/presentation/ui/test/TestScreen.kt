package com.elvitalya.presentation.ui.test

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elvitalya.domain.entity.Question
import com.elvitalya.presentation.R
import com.elvitalya.presentation.core.AppBar
import com.elvitalya.presentation.core.noRippleClickable
import com.elvitalya.presentation.core.rippleClickable
import com.elvitalya.presentation.theme.accent
import com.elvitalya.presentation.theme.black
import com.elvitalya.presentation.theme.white
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun TestScreen(
    viewModel: TestViewModel = koinViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val question by viewModel.question.collectAsStateWithLifecycle(
        initialValue = Question.EMPTY,
        lifecycle = lifecycle
    )

    Screen(
        question = question,
        onNextQuestionClick = viewModel::getRandomQuestion
    )
}


@Composable
private fun Screen(
    question: Question,
    onNextQuestionClick: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        delay(1000)
        onNextQuestionClick()
    }

    var questionExpanded by remember { mutableStateOf(false) }
    var instructionsDialogState by remember { mutableStateOf(false) }
    val text = if (questionExpanded) question.text else question.title


        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {

            AppBar(
                title = stringResource(id = R.string.test_title),
                icon = Icons.Default.Help,
                onIconClick = { instructionsDialogState = instructionsDialogState.not() }
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(6.dp, shape = RoundedCornerShape(16.dp), clip = true)
                    .background(white, RoundedCornerShape(16.dp))
                    .noRippleClickable { questionExpanded = questionExpanded.not() }
                    .verticalScroll(rememberScrollState())
                    .animateContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
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
                    .clip(RoundedCornerShape(16.dp))
                    .rippleClickable(onClick = {
                        questionExpanded = false
                        onNextQuestionClick()
                    })
                    .background(accent),
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

        if (instructionsDialogState) {
            InstructionDialog(onClose = { instructionsDialogState = false })
        }
}

@Composable
private fun InstructionDialog(
    onClose: () -> Unit
) {
    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxWidth()
                .background(accent)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.test_dialog_desc),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                lineHeight = 22.sp
            )
        }
    }
}