package com.elvitalya.presentation.core.main_activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.navigation.compose.rememberNavController
import com.elvitalya.presentation.core.LoadingBanner
import com.elvitalya.presentation.core.ViewState
import com.elvitalya.presentation.core.graphs.RootNavigationGraph
import org.koin.androidx.compose.koinViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainActivityViewModel = koinViewModel()
            Crossfade(targetState = viewModel.viewState, label = "") { state ->
                if (state == ViewState.Loading) LoadingBanner()
                else RootNavigationGraph(
                    navController = rememberNavController(),
                    isAuthorized = viewModel.isAuthorized
                )
            }
        }
    }
}

