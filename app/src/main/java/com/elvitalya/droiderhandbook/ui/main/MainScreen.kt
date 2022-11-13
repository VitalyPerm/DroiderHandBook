package com.elvitalya.droiderhandbook.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elvitalya.droiderhandbook.data.TestDetails
import com.elvitalya.droiderhandbook.ui.SignInScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Main.route) {
            MainScreen(
                onNavigateToDetails = { navController.navigate(Screens.Details.createRoute(it)) },
            )
        }

        composable(Screens.Registration.route) {
            SignInScreen(onNavigateToMain = { navController.navigate(Screens.Main.route) })
        }
        composable(
            Screens.Details.route,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) {
            DetailScreen(
                it.arguments?.getString("title")
            )
        }
    }
}

@Composable
fun DetailScreen(
    title: String?,
    viewModel: GlobalViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(key1 = Unit, block = { viewModel.getByTitle(title ?: "") })
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(viewModel.detailList) {
                    DetailItem(it)
                }
            }

        }
    }
}

@Composable
fun DetailItem(
    item: TestDetails
) {

    var visible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = item.title ?: "empty", modifier = Modifier
                .padding(16.dp)
                .clickable { visible = visible.not() }
        )

        AnimatedVisibility(visible = visible) {
            Text(text = item.text ?: "empty")
        }
    }
}


@Composable
fun MainScreen(
    onNavigateToDetails: (String) -> Unit,
    viewModel: GlobalViewModel = viewModel(),
) {

    LaunchedEffect(key1 = Unit, block = { viewModel.loadData() })
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(viewModel.list) {
                Text(
                    text = it.title ?: "empty", modifier = Modifier
                        .padding(16.dp)
                        .clickable { onNavigateToDetails(it.title ?: "") }
                )
            }
        }

    }
}