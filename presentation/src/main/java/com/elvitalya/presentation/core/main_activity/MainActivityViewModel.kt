package com.elvitalya.presentation.core.main_activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.repository.AuthRepository
import com.elvitalya.presentation.core.ViewState
import kotlinx.coroutines.launch

class MainActivityViewModel(
    authRepository: AuthRepository
) : ViewModel() {

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    var isAuthorized by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            isAuthorized = authRepository.isAuthorized()
            viewState = ViewState.Content
        }

    }

}