package com.elvitalya.droiderhandbook.ui.main

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.navigation.BottomNavigationScreen
import com.elvitalya.droiderhandbook.navigation.Destinations
import com.elvitalya.droiderhandbook.ui.favorite.FavoriteScreen
import com.elvitalya.droiderhandbook.ui.questiondetail.QuestionDetailsScreen
import com.elvitalya.droiderhandbook.ui.search.SearchScreen
import com.elvitalya.droiderhandbook.ui.sections.ReloadQuestionsAlertDialog
import com.elvitalya.droiderhandbook.ui.sections.SectionsScreen
import com.elvitalya.droiderhandbook.ui.GlobalViewModel
import com.elvitalya.droiderhandbook.ui.test.TestScreen
import com.elvitalya.droiderhandbook.ui.theme.ColorsScreen
import com.elvitalya.droiderhandbook.navigation.NavConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    globalViewModel: GlobalViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var reloadQuestionsAlertDialogState by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = stringResource(
                    id = getTopAppBarTitle(
                        currentDestination?.route
                    )
                ), modifier = Modifier
                    .clickable(enabled = currentDestination?.route == BottomNavigationScreen.Sections.route) {
                        navController.navigate(Destinations.ColorsScreen.route)
                    }, color = MaterialTheme.colorScheme.onTertiary
            )
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ), actions = {
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
        })
    }, bottomBar = {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.tertiary,
        ) {
            bottomNavigationItems.forEach { screen ->
                val selected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                val iconTintColor by animateColorAsState(targetValue = if (selected) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSurface)
                BottomNavigationItem(icon = {
                    val image = when (screen) {
                        BottomNavigationScreen.Favorite -> Icons.Filled.Favorite
                        BottomNavigationScreen.Search -> Icons.Filled.Search
                        BottomNavigationScreen.Sections -> Icons.Filled.List
                        BottomNavigationScreen.Test -> Icons.Filled.Book
                    }
                    Icon(
                        imageVector = image, contentDescription = null, tint = iconTintColor
                    )
                }, selected = selected, onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // re-selecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                })
            }
        }
    }) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomNavigationScreen.Sections.route,
            Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            composable(BottomNavigationScreen.Sections.route) { SectionsScreen(navController) }
            composable(BottomNavigationScreen.Favorite.route) { FavoriteScreen(navController) }
            composable(BottomNavigationScreen.Search.route) { SearchScreen(navController) }
            composable(BottomNavigationScreen.Test.route) { TestScreen(navController) }
            composable(Destinations.ColorsScreen.route) { ColorsScreen() }
            composable(
                route = Destinations.QuestionDetail.route,
                arguments = listOf(
                    navArgument(
                        name = NavConstants.QUESTION_ID,
                        builder = { type = NavType.IntType })
                )
            ) {
                QuestionDetailsScreen(id = it.arguments?.getInt(NavConstants.QUESTION_ID) ?: 0)
            }
        }
    }

    ReloadQuestionsAlertDialog(state = reloadQuestionsAlertDialogState, onYesClick = {
        reloadQuestionsAlertDialogState = false
        globalViewModel.reloadQuestions()
    }, onNoClick = { reloadQuestionsAlertDialogState = false })
}

private val bottomNavigationItems = listOf(
    BottomNavigationScreen.Sections,
    BottomNavigationScreen.Favorite,
    BottomNavigationScreen.Search,
    BottomNavigationScreen.Test
)


private fun getTopAppBarTitle(route: String?): Int = when (route) {
    BottomNavigationScreen.Favorite.route -> BottomNavigationScreen.Favorite.resourceId
    BottomNavigationScreen.Search.route -> BottomNavigationScreen.Search.resourceId
    BottomNavigationScreen.Sections.route -> BottomNavigationScreen.Sections.resourceId
    BottomNavigationScreen.Test.route -> BottomNavigationScreen.Test.resourceId
    Destinations.QuestionDetail.route -> Destinations.QuestionDetail.resourceId
    Destinations.ColorsScreen.route -> Destinations.ColorsScreen.resourceId
    else -> R.string.empty_string
}