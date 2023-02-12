package com.elvitalya.droiderhandbook.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.elvitalya.droiderhandbook.presentation.core.MainNavigation
import kotlinx.coroutines.flow.MutableStateFlow

class MainFlowViewModel : ViewModel() {

    val selectedPosition = MutableStateFlow(MainNavigation.SECTIONS)

}