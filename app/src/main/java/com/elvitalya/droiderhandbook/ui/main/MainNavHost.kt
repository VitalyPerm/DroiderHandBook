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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = currentDestination?.route ?: "") }
            )
        },
        bottomBar = {
            BottomNavigation {

                bottomNavigationItems.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            val image = when (screen) {
                                BottomNavigationScreen.Favorite -> Icons.Filled.Favorite
                                BottomNavigationScreen.Search -> Icons.Filled.Search
                                BottomNavigationScreen.Sections -> Icons.Filled.List
                                BottomNavigationScreen.Test -> Icons.Filled.Book
                            }
                            Icon(image, contentDescription = null)
                        },
                        //     label = { Text(stringResource(screen.resourceId)) },
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
            startDestination = BottomNavigationScreen.Sections.route,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavigationScreen.Sections.route) { SectionsScreen(navController) }
            composable(BottomNavigationScreen.Favorite.route) { FavoriteScreen(navController) }
            composable(BottomNavigationScreen.Search.route) { SearchScreen(navController) }
            composable(BottomNavigationScreen.Test.route) { TestScreen(navController) }
        }
    }
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
}