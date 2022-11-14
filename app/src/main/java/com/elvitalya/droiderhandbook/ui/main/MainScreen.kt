package com.elvitalya.droiderhandbook.ui.main

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.data.TestDetails
import com.elvitalya.droiderhandbook.ui.SignInScreen

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainNavHost(
//    modifier: Modifier = Modifier,
//    navController: NavHostController = rememberNavController(),
//    startDestination: String
//) {
//
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text(text = "Main Title") })
//        },
//        bottomBar = {
//            BottomNavigation {
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentDestination = navBackStackEntry?.destination
//            }
//
//        }
//    ) { innerPadding ->
//        NavHost(
//            navController,
//            startDestination = Screen.Profile.route,
//            Modifier.padding(innerPadding)
//        ) {
//            composable(Screen.Profile.route) { Profile(navController) }
//            composable(Screen.FriendsList.route) { FriendsList(navController) }
//        }
//    }
//}
//
//val items = listOf(
//    Screen.Profile,
//    Screen.FriendsList,
//)
//sealed class Screen(val route: String, @StringRes val resourceId: Int) {
//    object Profile : Screen("profile", R.string.profile)
//    object FriendsList : Screen("friendslist", R.string.friends_list)
//}
//
//
//
//    @Composable
//    fun DetailScreen(
//        title: String?,
//        viewModel: GlobalViewModel = viewModel()
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Cyan),
//            contentAlignment = Alignment.Center
//        ) {
//            LaunchedEffect(key1 = Unit, block = { viewModel.getByTitle(title ?: "") })
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//            ) {
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    items(viewModel.detailList) {
//                        DetailItem(it)
//                    }
//                }
//
//            }
//        }
//    }
//
//    @Composable
//    fun DetailItem(
//        item: TestDetails
//    ) {
//
//        var visible by remember { mutableStateOf(false) }
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//            Text(
//                text = item.title ?: "empty", modifier = Modifier
//                    .padding(16.dp)
//                    .clickable { visible = visible.not() }
//            )
//
//            AnimatedVisibility(visible = visible) {
//                Text(text = item.text ?: "empty")
//            }
//        }
//    }
//
//
//    @Composable
//    fun MainScreen(
//        onNavigateToDetails: (String) -> Unit,
//        viewModel: GlobalViewModel = viewModel(),
//    ) {
//
//        LaunchedEffect(key1 = Unit, block = { viewModel.loadData() })
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                items(viewModel.list) {
//                    Text(
//                        text = it.title ?: "empty", modifier = Modifier
//                            .padding(16.dp)
//                            .clickable { onNavigateToDetails(it.title ?: "") }
//                    )
//                }
//            }
//
//        }
//    }