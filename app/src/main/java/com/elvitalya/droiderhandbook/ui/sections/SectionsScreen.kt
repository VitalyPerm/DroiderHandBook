package com.elvitalya.droiderhandbook.ui.sections

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG

@Composable
fun SectionsScreen(
    navController: NavHostController,
    vm: SectionsViewModel = hiltViewModel()
) {

    val sections by vm.sectionsList.collectAsState()

    val javaQuestions by vm.javaQuestions.collectAsState()
    val androidQuestions by vm.androidQuestions.collectAsState()
    val kotlinQuestions by vm.kotlinQuestions.collectAsState()
    val basicQuestions by vm.basicQuestions.collectAsState()


    LaunchedEffect(key1 = Unit) {
        Log.d(TAG, "SectionsScreen: Launch effect called!")
        vm.getSections()
        vm.getQuestions()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        sections.forEach { sectionTitle ->
            SectionItem(
                title = sectionTitle,
                questions = when (sectionTitle) {
                    "Java" -> javaQuestions
                    "Kotlin" -> kotlinQuestions
                    "android" -> androidQuestions
                    else -> basicQuestions
                }
            )
        }
    }
}

@Composable
private fun SectionItem(
    title: String,
    questions: List<QuestionEntity>
) {

    var isExpanded by remember { mutableStateOf(false) }

    Text(
        text = title,
        modifier = Modifier
            .padding(8.dp)
            .background(
                MaterialTheme.colorScheme.inversePrimary,
                RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                isExpanded = isExpanded.not()
            }
            .padding(16.dp)


    )
    AnimatedVisibility(visible = isExpanded) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(questions) { question ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = question.title ?: "", modifier = Modifier
                            .padding(8.dp)
                    )
                    Text(
                        text = question.text?.substring(0..50) ?: "",
                        modifier = Modifier
                            .padding(8.dp)
                    )

                }
            }
        }
    }


}