package com.elvitalya.droiderhandbook.ui.main

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.ui.favorite.FavoriteScreen
import com.elvitalya.droiderhandbook.ui.questiondetail.QuestionDetailsScreen
import com.elvitalya.droiderhandbook.ui.search.SearchScreen
import com.elvitalya.droiderhandbook.ui.sections.ReloadQuestionsAlertDialog
import com.elvitalya.droiderhandbook.ui.sections.SectionsScreen
import com.elvitalya.droiderhandbook.ui.sections.SectionsViewModel
import com.elvitalya.droiderhandbook.ui.test.TestScreen
import com.elvitalya.droiderhandbook.ui.theme.ColorsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    sectionsViewModel: SectionsViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var reloadQuestionsAlertDialogState by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = BottomNavigationScreen.getTitle(
                                currentDestination?.route
                            )
                        ),
                        modifier = Modifier
                            // костыль что б глянуть палитру
                            .clickable(enabled = currentDestination?.route == BottomNavigationScreen.Sections.route) {
                                navController.navigate(Destinations.ColorsScreen.route)
                            },
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                actions = {
                    AnimatedVisibility(currentDestination?.route == BottomNavigationScreen.Sections.route) {
                        IconButton(onClick = {
                            reloadQuestionsAlertDialogState = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                null,
                                tint = MaterialTheme.colorScheme.onTertiary
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.tertiary,
            ) {

                bottomNavigationItems.forEach { screen ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    val iconTintColor by animateColorAsState(targetValue = if (selected) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSurface)
                    BottomNavigationItem(
                        icon = {
                            val image = when (screen) {
                                BottomNavigationScreen.Favorite -> Icons.Filled.Favorite
                                BottomNavigationScreen.Search -> Icons.Filled.Search
                                BottomNavigationScreen.Sections -> Icons.Filled.List
                                BottomNavigationScreen.Test -> Icons.Filled.Book
                            }
                            Icon(
                                imageVector = image,
                                contentDescription = null,
                                tint = iconTintColor
                            )
                        },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomNavigationScreen.Sections.route,
            Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            composable(BottomNavigationScreen.Sections.route) {
                SectionsScreen(navController) {
                    //  navController.navigate(Destinations.QuestionDetail.route)
                }
            }
            composable(BottomNavigationScreen.Favorite.route) { FavoriteScreen(navController) }
            composable(BottomNavigationScreen.Search.route) { SearchScreen(navController) }
            composable(BottomNavigationScreen.Test.route) { TestScreen(navController) }
            composable(Destinations.QuestionDetail.route) { QuestionDetailsScreen() }
            composable(Destinations.ColorsScreen.route) { ColorsScreen() }
        }
    }

    ReloadQuestionsAlertDialog(
        state = reloadQuestionsAlertDialogState,
        onYesClick = {
            reloadQuestionsAlertDialogState = false
            sectionsViewModel.reloadQuestions()
        },
        onNoClick = { reloadQuestionsAlertDialogState = false }
    )
}

val bottomNavigationItems = listOf(
    BottomNavigationScreen.Sections,
    BottomNavigationScreen.Favorite,
    BottomNavigationScreen.Search,
    BottomNavigationScreen.Test
)

sealed class BottomNavigationScreen(val route: String, @StringRes val resourceId: Int) {
    object Sections : BottomNavigationScreen("sections", R.string.sections)
    object Favorite : BottomNavigationScreen("favorite", R.string.favorite)
    object Search : BottomNavigationScreen("search", R.string.search)
    object Test : BottomNavigationScreen("test", R.string.test)

    companion object {
        fun getTitle(route: String?): Int = when (route) {
            Favorite.route -> Favorite.resourceId
            Search.route -> Search.resourceId
            Sections.route -> Sections.resourceId
            Test.route -> Test.resourceId
            else -> R.string.empty_string
        }
    }
}

sealed class Destinations(val route: String) {
    object QuestionDetail : Destinations("detail")
    object ColorsScreen : Destinations("colors")
}