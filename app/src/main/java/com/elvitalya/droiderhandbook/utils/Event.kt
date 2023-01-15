package com.elvitalya.droiderhandbook.utils

sealed class Event<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Event<T>(data)
    class Error<T>(message: String, data: T? = null) : Event<T>(data, message)
    class Loading<T> : Event<T>()
}