package com.elvitalya.presentation.core.graphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.elvitalya.presentation.ui.authentication.auth.AuthScreen
import com.elvitalya.presentation.ui.authentication.registration.RegistrationScreen
import com.elvitalya.presentation.ui.select_auth_method.SelectAuthMethodScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = RootGraph.AUTHENTICATION,
        startDestination = AuthGraph.SelectAuthMethod.route
    ) {
        composable(route = AuthGraph.SelectAuthMethod.route) {
            SelectAuthMethodScreen(
                onRegClick = { navController.navigate(AuthGraph.Registration.route) },
                onAuthClick = { navController.navigate(AuthGraph.Auth.route) }
            )
        }

        composable(route = AuthGraph.Auth.route) {
            AuthScreen(
                navigateToHomeScreen = {
                    navController.popBackStack()
                    navController.navigate(RootGraph.MAIN)
                }
            )
        }

        composable(route = AuthGraph.Registration.route) {
            RegistrationScreen()
        }
    }
}

sealed class AuthGraph(val route: String) {
    object SelectAuthMethod : AuthGraph(route = "select_auth_method")
    object Auth : AuthGraph(route = "auth")
    object Registration : AuthGraph(route = "registration")
}