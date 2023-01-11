package com.elvitalya.droiderhandbook.ui.questiondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.droiderhandbook.data.model.QuestionEntity

@Composable
fun QuestionDetailsScreen(
    id: Int,
    question: QuestionEntity
) {

  //  LaunchedEffect(key1 = Unit, block = { vm.getQuestionById(id) })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {

        Text(
            text = question.title ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = question.text ?: "",
            modifier = Modifier
                .padding(8.dp)
        )

    }
}