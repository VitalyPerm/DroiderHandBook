package com.elvitalya.droiderhandbook.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.elvitalya.droiderhandbook.ui.theme.DroiderHandBookTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "check___"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: is Auth = ${Firebase.auth.currentUser.toString()}")
        setContent {
            DroiderHandBookTheme {
                MainNavHost(
                    startDestination = if (Firebase.auth.currentUser == null) Screens.Registration.route else Screens.Main.route
                )
            }
        }

    }
}


sealed class Screens(val route: String) {
    object Registration : Screens("registration")
    object Main : Screens("main")
    object Details : Screens("details/{title}") {
        fun createRoute(title: String) = "details/${title}"
    }
}