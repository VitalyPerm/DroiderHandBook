package com.elvitalya.droiderhandbook.ui.sections

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
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
import com.elvitalya.droiderhandbook.navigation.Destinations
import com.elvitalya.droiderhandbook.ui.GlobalViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SectionsScreen(
    navController: NavHostController,
    vm: GlobalViewModel = hiltViewModel()
) {

    val javaQuestions by vm.javaQuestions.collectAsState(emptyList())
    val androidQuestions by vm.androidQuestions.collectAsState(emptyList())
    val kotlinQuestions by vm.kotlinQuestions.collectAsState(emptyList())
    val basicQuestions by vm.basicQuestions.collectAsState(emptyList())

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
        AnimatedContent(targetState = loading) { loading ->
            if (loading.not()) {
                Content(
                    javaQuestions = javaQuestions,
                    kotlinQuestions = kotlinQuestions,
                    androidQuestions = androidQuestions,
                    basicQuestions = basicQuestions,
                    onQuestionClick = { questionId ->
                        navController.navigate(
                            Destinations.QuestionDetail.createRoute(questionId)
                        )
                    },
                    expandedSections = expandedSections,
                    onSectionClick = {
                        expandedSections = when (it) {
                            Sections.Java -> expandedSections.copy(java = expandedSections.java.not())
                            Sections.Kotlin -> expandedSections.copy(kotlin = expandedSections.kotlin.not())
                            Sections.Android -> expandedSections.copy(android = expandedSections.android.not())
                            Sections.Basic -> expandedSections.copy(basic = expandedSections.basic.not())
                        }
                    },
                    onFavoriteClick = { vm.updateQuestion(question = it.copy(favorite = it.favorite.not())) }
                )
            } else {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .size(100.dp)
                )
            }
        }
    }
}

@Composable
private fun Content(
    javaQuestions: List<QuestionEntity>,
    kotlinQuestions: List<QuestionEntity>,
    androidQuestions: List<QuestionEntity>,
    basicQuestions: List<QuestionEntity>,
    onQuestionClick: (Int) -> Unit,
    expandedSections: SectionScreenContentVisibility,
    onSectionClick: (Sections) -> Unit,
    onFavoriteClick: (QuestionEntity) -> Unit,
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
                    onQuestionClick = onQuestionClick,
                    onFavoriteClick = onFavoriteClick
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
                    onQuestionClick = onQuestionClick,
                    onFavoriteClick = onFavoriteClick
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
                    onQuestionClick = onQuestionClick,
                    onFavoriteClick = onFavoriteClick
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
                    onQuestionClick = onQuestionClick,
                    onFavoriteClick = onFavoriteClick
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
    onQuestionClick: (Int) -> Unit,
    onFavoriteClick: (QuestionEntity) -> Unit,
) {

    val favoriteIconTint by animateColorAsState(
        targetValue = if (questionEntity.favorite) MaterialTheme.colorScheme.tertiary
        else MaterialTheme.colorScheme.onTertiary
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable { onQuestionClick(questionEntity.id) }

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = questionEntity.title,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.Center),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = { onFavoriteClick(questionEntity) },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = favoriteIconTint
                )
            }
        }
        Text(
            text = questionEntity.text,
            modifier = Modifier
                .padding(8.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

    }
}

@Composable
fun ReloadQuestionsAlertDialog(
    state: Boolean,
    onYesClick: () -> Unit,
    onNoClick: () -> Unit
) {
    AnimatedVisibility(visible = state) {
        AlertDialog(
            title = {
                Text(
                    text = "Хотите обновить список вопросов?",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            },
            shape = RoundedCornerShape(8.dp),
            onDismissRequest = onNoClick,
            confirmButton = {
                TextButton(onClick = onYesClick) {
                    Text(
                        text = "Да",
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(
                                MaterialTheme.colorScheme.tertiary,
                                RoundedCornerShape(16.dp)
                            )
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onNoClick) {
                    Text(
                        text = "Нет",
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(
                                MaterialTheme.colorScheme.tertiary,
                                RoundedCornerShape(16.dp)
                            )
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        )
    }
}

data class SectionScreenContentVisibility(
    val kotlin: Boolean = false,
    val java: Boolean = false,
    val android: Boolean = false,
    val basic: Boolean = false,
)