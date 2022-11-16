package com.elvitalya.droiderhandbook.utils

sealed class ViewState {
    object Loading : ViewState()
    object Content : ViewState()
    object Error : ViewState()
    object Empty : ViewState()
}
