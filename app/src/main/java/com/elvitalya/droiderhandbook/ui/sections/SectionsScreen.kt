package com.elvitalya.droiderhandbook.ui.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.core.EmptyBanner
import com.elvitalya.droiderhandbook.ui.core.ErrorBanner
import com.elvitalya.droiderhandbook.ui.core.LoadingBanner
import com.elvitalya.droiderhandbook.ui.core.rippleClickable
import com.elvitalya.droiderhandbook.ui.theme.accent
import com.elvitalya.droiderhandbook.ui.theme.black
import com.elvitalya.droiderhandbook.utils.ViewState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun SectionsScreen(
    javaQuestions: List<QuestionEntity>,
    androidQuestions: List<QuestionEntity>,
    kotlinQuestions: List<QuestionEntity>,
    basicQuestions: List<QuestionEntity>,
    viewState: ViewState,
    onQuestionClick: (Long) -> Unit,
    onFavoriteClick: (QuestionEntity) -> Unit,
    onReloadClick: () -> Unit
) {

    var expandedSections by remember { mutableStateOf(SectionScreenContentVisibility()) }
    var reloadDialogVisible by remember { mutableStateOf(false) }

    ProvideWindowInsets {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            Crossfade(targetState = viewState) { viewState ->
                when (viewState) {
                    ViewState.Content ->
                        Content(
                            javaQuestions = javaQuestions,
                            kotlinQuestions = kotlinQuestions,
                            androidQuestions = androidQuestions,
                            basicQuestions = basicQuestions,
                            onQuestionClick = onQuestionClick,
                            expandedSections = expandedSections,
                            onSectionClick = { section ->
                                expandedSections = when (section) {
                                    Sections.Java -> expandedSections.copy(java = expandedSections.java.not())
                                    Sections.Kotlin -> expandedSections.copy(kotlin = expandedSections.kotlin.not())
                                    Sections.Android -> expandedSections.copy(android = expandedSections.android.not())
                                    Sections.Basic -> expandedSections.copy(basic = expandedSections.basic.not())
                                    Sections.Coroutines -> expandedSections.copy(basic = expandedSections.coroutines.not())
                                }
                            },
                            onFavoriteClick = onFavoriteClick,
                            onReloadClick = { reloadDialogVisible = true }
                        )

                    ViewState.Error -> ErrorBanner()
                    ViewState.Loading -> LoadingBanner()
                    ViewState.Empty -> EmptyBanner()
                }
            }
            if (reloadDialogVisible) {
                ReloadDialog(
                    onReloadClick = {
                        reloadDialogVisible = false
                        onReloadClick()
                    },
                    onCancelClick = { reloadDialogVisible = false }
                )
            }
        }
    }
}

@Composable
private fun ReloadDialog(
    onReloadClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelClick,
        title = {
            Text(text = stringResource(id = R.string.sections_alert_title))
        },
        confirmButton = {
            Text(
                text = stringResource(id = R.string.sections_alert_yes),
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                color = black,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .rippleClickable(onReloadClick)
                    .background(accent)
                    .padding(8.dp)

            )
        },
        dismissButton = {
            Text(
                text = stringResource(id = R.string.sections_alert_no),
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                color = black,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .rippleClickable(onCancelClick)
                    .background(accent)
                    .padding(8.dp)

            )
        }
    )
}

@Composable
private fun Content(
    javaQuestions: List<QuestionEntity>,
    kotlinQuestions: List<QuestionEntity>,
    androidQuestions: List<QuestionEntity>,
    basicQuestions: List<QuestionEntity>,
    onQuestionClick: (Long) -> Unit,
    expandedSections: SectionScreenContentVisibility,
    onSectionClick: (Sections) -> Unit,
    onFavoriteClick: (QuestionEntity) -> Unit,
    onReloadClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        if (expandedSections.allInvisible) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(accent)
                    .rippleClickable(onReloadClick)
            ) {
                Image(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(6.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
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

}

@Composable
fun SectionTitle(
    section: Sections,
    onSectionClick: (Sections) -> Unit
) {
    Text(
        text = stringResource(id = section.nameRes),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(accent, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .rippleClickable({ onSectionClick(section) })
            .padding(16.dp),
        fontSize = 28.sp,
        textAlign = TextAlign.Center,
        color = black
    )
}

@Composable
fun SectionContentItem(
    questionEntity: QuestionEntity,
    onQuestionClick: (Long) -> Unit,
    onFavoriteClick: (QuestionEntity) -> Unit,
) {

    val favoriteIconTint by animateColorAsState(
        targetValue = if (questionEntity.favorite) accent
        else black
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .border(
                BorderStroke(1.dp, accent),
                RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .rippleClickable({ onQuestionClick(questionEntity.id) })
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 6.dp)
        ) {
            Text(
                text = questionEntity.title,
                modifier = Modifier
                    .align(Alignment.Center),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Image(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape)
                    .rippleClickable({ onFavoriteClick(questionEntity) })
                    .padding(6.dp),
                colorFilter = ColorFilter.tint(favoriteIconTint)
            )
        }
        Text(
            text = questionEntity.text,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 6.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

    }
}

data class SectionScreenContentVisibility(
    val kotlin: Boolean = false,
    val java: Boolean = false,
    val android: Boolean = false,
    val basic: Boolean = false,
    val coroutines: Boolean = false
) {
    val allInvisible
        get() = kotlin.not() && java.not() && android.not() && basic.not() && coroutines.not()
}