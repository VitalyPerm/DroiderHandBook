package com.elvitalya.droiderhandbook.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.ui.theme.DroiderHandBookTheme

class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "check___"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DroiderHandBookTheme {
                MainNavHost()
            }
        }

    }
}
