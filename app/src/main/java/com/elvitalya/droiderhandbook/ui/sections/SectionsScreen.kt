package com.elvitalya.droiderhandbook.ui.sections

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG

@Composable
fun SectionsScreen(
    navController: NavHostController,
    vm: SectionsViewModel = hiltViewModel(),
    onQuestionClick: () -> Unit
) {

    val sections by vm.sectionsList.collectAsState()

    val javaQuestions by vm.javaQuestions.collectAsState()
    val androidQuestions by vm.androidQuestions.collectAsState()
    val kotlinQuestions by vm.kotlinQuestions.collectAsState()
    val basicQuestions by vm.basicQuestions.collectAsState()

    var selectedSection by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(key1 = Unit) {
        Log.d(TAG, "SectionsScreen: Launch effect called!")
        vm.getQuestions()
        vm.getSections()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(targetState = javaQuestions.isNotEmpty()) { notEmpty ->
            if (notEmpty) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Sections.values().forEach { section ->
                        SectionItem(
                            title = section.name,
                            questions = when (section) {
                                Sections.Java -> javaQuestions
                                Sections.Kotlin -> kotlinQuestions
                                Sections.Android -> androidQuestions
                                Sections.Basic -> basicQuestions
                            },
                            onQuestionClick = onQuestionClick,
                            onSectionSelected = {
                                selectedSection = if (it == selectedSection) null else it
                            },
                            selectedSection = selectedSection
                        )
                    }
                }
            } else {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .size(150.dp)
                )
            }
        }
    }

}

@Composable
private fun SectionItem(
    title: String,
    questions: List<QuestionEntity>,
    onQuestionClick: () -> Unit,
    onSectionSelected: (String) -> Unit,
    selectedSection: String?
) {
    Log.d(TAG, "SectionItem: $selectedSection")
    var isExpanded by remember { mutableStateOf(false) }
    val visible = selectedSection == title || selectedSection == null
    if (visible) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    MaterialTheme.colorScheme.tertiary,
                    RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    isExpanded = isExpanded.not()
                    onSectionSelected(title)
                }
                .padding(16.dp),
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onTertiary
        )
        AnimatedVisibility(isExpanded) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                items(questions) { question ->

                    val text =
                        question.text.run { if (length > 50) this else this.substring(0, 50) }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onQuestionClick() }
                            .padding(4.dp)
                            .border(
                                BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                                RoundedCornerShape(16.dp)
                            )
                    ) {
                        Text(
                            text = question.title ?: "", modifier = Modifier
                                .padding(8.dp)
                        )
                        Text(
                            text = text,
                            modifier = Modifier
                                .padding(8.dp)
                        )

                    }
                }
            }
        }
    }

}