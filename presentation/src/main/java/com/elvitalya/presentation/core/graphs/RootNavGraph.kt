package com.elvitalya.presentation.core.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elvitalya.presentation.ui.main.MainScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    isAuthorized: Boolean
) {
    NavHost(
        navController = navController,
        route = RootGraph.ROOT,
        startDestination = if (isAuthorized) RootGraph.MAIN else RootGraph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(route = RootGraph.MAIN) {
            MainScreen()
        }
    }

}

object RootGraph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val MAIN = "main_graph"
}