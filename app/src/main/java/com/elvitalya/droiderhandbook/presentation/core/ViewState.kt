package com.elvitalya.droiderhandbook.presentation.core

sealed interface ViewState {
    object Loading : ViewState
    object Content : ViewState
    object Error : ViewState
    object Empty: ViewState
}