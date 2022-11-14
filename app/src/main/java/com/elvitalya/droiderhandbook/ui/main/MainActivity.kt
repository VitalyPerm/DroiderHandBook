package com.elvitalya.droiderhandbook.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.ui.SignInScreen
import com.elvitalya.droiderhandbook.ui.theme.DroiderHandBookTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "check___"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isRegistered by remember { mutableStateOf(false) }
            isRegistered = Firebase.auth.currentUser != null
            DroiderHandBookTheme {
                Crossfade(
                    targetState = isRegistered,
                    animationSpec = tween(3000)
                ) { registered ->
                    if (registered) MainNavHost()
                    else SignInScreen {
                        isRegistered = true
                    }
                }
            }
        }

    }
}
