package com.elvitalya.droiderhandbook.ui.sections

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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

    val javaQuestions by vm.javaQuestions.collectAsState()
    val androidQuestions by vm.androidQuestions.collectAsState()
    val kotlinQuestions by vm.kotlinQuestions.collectAsState()
    val basicQuestions by vm.basicQuestions.collectAsState()

    val loading by vm.loading.collectAsState()

    var expandedSections by remember { mutableStateOf(SectionScreenContentVisibility()) }


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
                onQuestionClick = onQuestionClick,
                expandedSections = expandedSections,
                onSectionClick = {
                    expandedSections = when (it) {
                        Sections.Java -> expandedSections.copy(java = expandedSections.java.not())
                        Sections.Kotlin -> expandedSections.copy(kotlin = expandedSections.kotlin.not())
                        Sections.Android -> expandedSections.copy(android = expandedSections.android.not())
                        Sections.Basic -> expandedSections.copy(basic = expandedSections.basic.not())
                    }
                }
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
    expandedSections: SectionScreenContentVisibility,
    onSectionClick: (Sections) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            SectionTitle(
                section = Sections.Java,
                onSectionClick = onSectionClick
            )
        }

        items(javaQuestions) {
            AnimatedVisibility(visible = expandedSections.java) {
                SectionContentItem(
                    questionEntity = it,
                    onQuestionClick = onQuestionClick
                )
            }
        }

        item {
            SectionTitle(
                section = Sections.Kotlin,
                onSectionClick = onSectionClick
            )
        }

        items(kotlinQuestions) {
            AnimatedVisibility(visible = expandedSections.kotlin) {
                SectionContentItem(
                    questionEntity = it,
                    onQuestionClick = onQuestionClick
                )
            }
        }

        item {
            SectionTitle(
                section = Sections.Android,
                onSectionClick = onSectionClick
            )
        }
        items(androidQuestions) {
            AnimatedVisibility(visible = expandedSections.android) {
                SectionContentItem(
                    questionEntity = it,
                    onQuestionClick = onQuestionClick
                )
            }
        }

        item {
            SectionTitle(
                section = Sections.Basic,
                onSectionClick = onSectionClick
            )
        }
        items(basicQuestions) {
            AnimatedVisibility(visible = expandedSections.basic) {
                SectionContentItem(
                    questionEntity = it,
                    onQuestionClick = onQuestionClick
                )
            }
        }
    }
}

@Composable
fun SectionTitle(
    section: Sections,
    onSectionClick: (Sections) -> Unit
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
            .clickable { onSectionClick(section) }
            .padding(16.dp),
        fontSize = 28.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onTertiary
    )
}

@Composable
fun SectionContentItem(
    questionEntity: QuestionEntity,
    onQuestionClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable { onQuestionClick() }

    ) {
        Text(
            text = questionEntity.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = questionEntity.text,
            modifier = Modifier
                .padding(8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

    }
}

data class SectionScreenContentVisibility(
    val kotlin: Boolean = false,
    val java: Boolean = false,
    val android: Boolean = false,
    val basic: Boolean = false,
)