package com.elvitalya.droiderhandbook.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.ui.signin.SignInScreen
import com.elvitalya.droiderhandbook.ui.theme.DroiderHandBookTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "check___"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            var isRegistered by remember { mutableStateOf(false) }
            isRegistered = Firebase.auth.currentUser != null
            DroiderHandBookTheme {
                Crossfade(
                    targetState = isRegistered,
                    animationSpec = tween(1000)
                ) { registered ->
                    if (registered) MainNavHost()
                    else SignInScreen(
                        onSuccess = {
                            isRegistered = true
                            Toast.makeText(context, getString(R.string.success), Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                }
            }
        }

    }
}
