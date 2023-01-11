package com.elvitalya.droiderhandbook.utils

sealed interface ViewState {
    object Loading : ViewState
    object Content : ViewState
    object Error : ViewState
}