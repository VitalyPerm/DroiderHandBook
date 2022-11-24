package com.elvitalya.droiderhandbook.ui.favorite

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.elvitalya.droiderhandbook.navigation.Destinations
import com.elvitalya.droiderhandbook.ui.GlobalViewModel
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG
import com.elvitalya.droiderhandbook.ui.sections.SectionContentItem

@Composable
fun FavoriteScreen(
    navController: NavHostController,
    viewModel: GlobalViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getQuestions()
    }
    val favoriteQuestions by viewModel.favoriteQuestions.collectAsState(emptyList())

    Log.d(TAG, "FavoriteScreen: ${favoriteQuestions.size}")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(favoriteQuestions) {
            SectionContentItem(
                questionEntity = it,
                onQuestionClick = { questionId ->
                    navController.navigate(
                        Destinations.QuestionDetail.createRoute(questionId)
                    )
                },
                onFavoriteClick = { viewModel.updateQuestion(question = it.copy(favorite = it.favorite.not())) }
            )
        }
    }
}