package com.elvitalya.droiderhandbook.utils

sealed class Result {
    object Success : Result()
    data class Error(val message: String) : Result()
}