package com.elvitalya.presentation.core.graphs

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elvitalya.presentation.R
import com.elvitalya.presentation.ui.favorite.FavoriteScreen
import com.elvitalya.presentation.ui.question_details.QuestionDetailsScreen
import com.elvitalya.presentation.ui.search.SearchScreen
import com.elvitalya.presentation.ui.sections.SectionsScreen
import com.elvitalya.presentation.ui.test.TestScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        route = RootGraph.MAIN,
        startDestination = MainGraph.Sections.route,
        modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding())

    ) {
        composable(route = MainGraph.Sections.route) {
            SectionsScreen(onQuestionClick = { questionId ->
                navController.navigate("details/${questionId}")
            })
        }
        composable(route = MainGraph.Search.route) {
            SearchScreen(onQuestionClick = { questionId ->
                navController.navigate("details/${questionId}")
            })
        }
        composable(route = MainGraph.Favorite.route) {
            FavoriteScreen(onQuestionClick = { questionId ->
                navController.navigate("details/${questionId}")
            })
        }

        composable(route = MainGraph.Test.route) {
            TestScreen()
        }

        composable(route = "details/{id}") { backStackEntry ->
            QuestionDetailsScreen(questionId = backStackEntry.arguments?.getString("id")) {
                navController.popBackStack()
            }
        }
    }
}

sealed class MainGraph(
    val route: String,
    @StringRes val title: Int,
    val icon: ImageVector
) {
    object Sections : MainGraph(
        route = "sections",
        title = R.string.sections,
        icon = Icons.Filled.List
    )

    object Search : MainGraph(
        route = "profile",
        title = R.string.search,
        icon = Icons.Filled.Search
    )

    object Favorite : MainGraph(
        route = "favorite",
        title = R.string.favorite,
        icon = Icons.Filled.Favorite
    )

    object Test : MainGraph(
        route = "test",
        title = R.string.test,
        icon = Icons.Filled.Book
    )
}
