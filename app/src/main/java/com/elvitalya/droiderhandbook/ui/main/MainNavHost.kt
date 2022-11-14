package com.elvitalya.droiderhandbook.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.ui.favorite.FavoriteScreen
import com.elvitalya.droiderhandbook.ui.search.SearchScreen
import com.elvitalya.droiderhandbook.ui.sections.SectionsScreen
import com.elvitalya.droiderhandbook.ui.test.TestScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomNavigationItems.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            val image = when (screen) {
                                Screen.Favorite -> Icons.Filled.Favorite
                                Screen.Search -> Icons.Filled.Search
                                Screen.Sections -> Icons.Filled.List
                                Screen.Test -> Icons.Filled.Book
                            }
                            Icon(image, contentDescription = null)
                        },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
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
                        },
                        alwaysShowLabel = false,
                        selectedContentColor = Color.Green,
                        unselectedContentColor = Color.Black
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Sections.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Sections.route) { SectionsScreen(navController) }
            composable(Screen.Favorite.route) { FavoriteScreen(navController) }
            composable(Screen.Search.route) { SearchScreen(navController) }
            composable(Screen.Test.route) { TestScreen(navController) }
        }
    }
}

val bottomNavigationItems = listOf(
    Screen.Sections,
    Screen.Favorite,
    Screen.Search,
    Screen.Test
)

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Sections : Screen("sections", R.string.sections)
    object Favorite : Screen("favorite", R.string.favorite)
    object Search : Screen("search", R.string.search)
    object Test : Screen("test", R.string.test)
}