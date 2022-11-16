package com.elvitalya.droiderhandbook.ui.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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

    val loading by vm.loading.collectAsState()

    var selectedSection by remember { mutableStateOf<Sections?>(null) }


    LaunchedEffect(key1 = Unit) {
        vm.getQuestions()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading.not()) {
            Content(
                javaQuestions = javaQuestions,
                kotlinQuestions = kotlinQuestions,
                androidQuestions = androidQuestions,
                basicQuestions = basicQuestions,
                onSectionSelected = {
                    selectedSection =
                        if (it == selectedSection) null else it
                },
                onQuestionClick = onQuestionClick,
                selectedSection = selectedSection
            )
        } else {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .size(150.dp)
            )
        }
    }
}

@Composable
private fun Content(
    javaQuestions: List<QuestionEntity>,
    kotlinQuestions: List<QuestionEntity>,
    androidQuestions: List<QuestionEntity>,
    basicQuestions: List<QuestionEntity>,
    onQuestionClick: () -> Unit,
    onSectionSelected: (Sections) -> Unit,
    selectedSection: Sections?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (selectedSection) {
            null -> {
                Sections.values().forEach { section ->
                    SectionItem(
                        section = section,
                        questions = when (section) {
                            Sections.Java -> javaQuestions
                            Sections.Kotlin -> kotlinQuestions
                            Sections.Android -> androidQuestions
                            Sections.Basic -> basicQuestions
                        },
                        onQuestionClick = onQuestionClick,
                        onSectionSelected = onSectionSelected,
                        selectedSection = selectedSection
                    )
                }
            }
            Sections.Java -> {
                SectionItem(
                    section = Sections.Java,
                    questions = javaQuestions,
                    onQuestionClick = onQuestionClick,
                    onSectionSelected = onSectionSelected,
                    selectedSection = selectedSection
                )
            }
            Sections.Kotlin -> {
                SectionItem(
                    section = Sections.Kotlin,
                    questions = kotlinQuestions,
                    onQuestionClick = onQuestionClick,
                    onSectionSelected = onSectionSelected,
                    selectedSection = selectedSection
                )
            }
            Sections.Android -> {
                SectionItem(
                    section = Sections.Android,
                    questions = androidQuestions,
                    onQuestionClick = onQuestionClick,
                    onSectionSelected = onSectionSelected,
                    selectedSection = selectedSection
                )
            }
            Sections.Basic -> {
                SectionItem(
                    section = Sections.Basic,
                    questions = basicQuestions,
                    onQuestionClick = onQuestionClick,
                    onSectionSelected = onSectionSelected,
                    selectedSection = selectedSection
                )
            }
        }
    }
}

@Composable
private fun SectionItem(
    section: Sections,
    questions: List<QuestionEntity>,
    onQuestionClick: () -> Unit,
    onSectionSelected: (Sections) -> Unit,
    selectedSection: Sections?
) {
    Text(
        text = section.name,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                MaterialTheme.colorScheme.tertiary,
                RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                onSectionSelected(section)
            }
            .padding(16.dp),
        fontSize = 28.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onTertiary
    )
    if (selectedSection == section) {
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