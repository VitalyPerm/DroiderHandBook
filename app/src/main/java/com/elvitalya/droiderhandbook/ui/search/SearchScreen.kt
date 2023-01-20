package com.elvitalya.droiderhandbook.ui.search

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.elvitalya.droiderhandbook.ui.sections.SectionContentItem
import com.elvitalya.droiderhandbook.ui.theme.*
import com.elvitalya.droiderhandbook.utils.ViewState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun SearchScreen(
    questions: List<QuestionEntity>,
    onFavoriteClick: (QuestionEntity) -> Unit,
    onQuestionClick: (String) -> Unit,
    viewState: ViewState,
    searchInput: String,
    onSearchInput: (String) -> Unit,
    onClearSearchInput: () -> Unit
) {
    ProvideWindowInsets {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .background(white),
        ) {

            SearchInput(
                searchInput = searchInput,
                onSearchInput = onSearchInput,
                onClearSearchInput = onClearSearchInput
            )

            Crossfade(targetState = viewState) { viewState ->
                when (viewState) {
                    ViewState.Content -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp)
                        ) {
                            items(questions) {
                                SectionContentItem(
                                    questionEntity = it,
                                    onQuestionClick = onQuestionClick,
                                    onFavoriteClick = onFavoriteClick
                                )
                            }
                        }
                    }

                    ViewState.Error -> ErrorBanner()
                    ViewState.Loading -> LoadingBanner()
                    ViewState.Empty -> EmptyBanner()
                }
            }
        }
    }
}

@Composable
private fun SearchInput(
    searchInput: String,
    onSearchInput: (String) -> Unit,
    onClearSearchInput: () -> Unit
) {
    BasicTextField(
        value = searchInput,
        onValueChange = onSearchInput,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textStyle = TextStyle(
            color = black,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 18.sp,
        ),
        singleLine = true,
        cursorBrush = SolidColor(accent),
        decorationBox = { innerTextField ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(inActive, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                innerTextField()

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (searchInput.isBlank()) {
                        Text(
                            text = stringResource(R.string.search_hint),
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            color = hint
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .rippleClickable(onClearSearchInput)
                            .padding(6.dp),
                        colorFilter = ColorFilter.tint(black)
                    )

                }
            }
        }
    )
}