package com.elvitalya.droiderhandbook.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainFlowViewModel @Inject constructor() : ViewModel() {

    val selectedPosition = MutableStateFlow(MainNavigation.SECTIONS)

}