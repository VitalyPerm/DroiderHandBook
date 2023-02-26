package com.elvitalya.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.elvitalya.presentation.core.MainNavigation
import kotlinx.coroutines.flow.MutableStateFlow

class MainFlowViewModel : ViewModel() {

    val selectedPosition = MutableStateFlow(MainNavigation.SECTIONS)

}