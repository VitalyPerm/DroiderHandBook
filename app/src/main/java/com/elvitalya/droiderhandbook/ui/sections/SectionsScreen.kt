package com.elvitalya.droiderhandbook.ui.sections

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG

@Composable
fun SectionsScreen(
    navController: NavHostController,
    vm: SectionsViewModel = viewModel()
) {

    val sections by vm.sectionsList.collectAsState()

    LaunchedEffect(key1 = Unit) {
        Log.d(TAG, "SectionsScreen: Launch effect called!")
        vm.getSections()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(sections) {
                Text(
                    text = it, modifier = Modifier
                        .padding(8.dp)
                        .background(
                            MaterialTheme.colorScheme.inversePrimary,
                            RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                        }
                        .padding(16.dp)


                )
            }
        }

    }
}