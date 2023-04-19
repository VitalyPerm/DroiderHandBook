package com.elvitalya.presentation.ui.search

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elvitalya.domain.entity.Question
import com.elvitalya.presentation.R
import com.elvitalya.presentation.core.EmptyBanner
import com.elvitalya.presentation.core.ErrorBanner
import com.elvitalya.presentation.core.LoadingBanner
import com.elvitalya.presentation.core.ViewState
import com.elvitalya.presentation.core.rippleClickable
import com.elvitalya.presentation.theme.accent
import com.elvitalya.presentation.theme.black
import com.elvitalya.presentation.theme.hint
import com.elvitalya.presentation.theme.inActive
import com.elvitalya.presentation.theme.white
import com.elvitalya.presentation.ui.sections.SectionContentItem
import org.koin.androidx.compose.koinViewModel


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onQuestionClick: (Long) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val questions by viewModel.correspondedQuestions.collectAsStateWithLifecycle(
        initialValue = emptyList(),
        lifecycle = lifecycle
    )
    val searchInput by viewModel.searchInput.collectAsStateWithLifecycle()

    Screen(
        questions = questions,
        onFavoriteClick = viewModel::onFavoriteClick,
        onQuestionClick = onQuestionClick,
        viewState = viewModel.viewState,
        searchInput = searchInput,
        onSearchInput = viewModel::onSearchInput
    )
}

@Composable
private fun Screen(
    questions: List<Question>,
    onFavoriteClick: (Question) -> Unit,
    onQuestionClick: (Long) -> Unit,
    viewState: ViewState,
    searchInput: String,
    onSearchInput: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(white),
    ) {

        SearchInput(
            searchInput = searchInput,
            onSearchInput = onSearchInput,
            onClearSearchInput = onSearchInput
        )

        Crossfade(targetState = viewState, label = "") { viewState ->
            when (viewState) {
                ViewState.Content -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp)
                    ) {
                        items(questions) {
                            SectionContentItem(
                                onQuestionClick = onQuestionClick,
                                onFavoriteClick = onFavoriteClick,
                                question = it
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

@Composable
private fun SearchInput(
    searchInput: String,
    onSearchInput: (String) -> Unit,
    onClearSearchInput: (String) -> Unit
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
                            .rippleClickable(onClick = { onClearSearchInput("") })
                            .padding(6.dp),
                        colorFilter = ColorFilter.tint(black)
                    )

                }
            }
        }
    )
}