package com.elvitalya.droiderhandbook.utils

import android.app.Application
import android.widget.Toast
import com.elvitalya.droiderhandbook.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToastDispatcher @Inject constructor(private val application: Application) {

    companion object {
        private const val MIN_INTERVAL = 3_000L
    }

    private var message: String? = null
    private var lastTimestamp: Long = 0L

    fun dispatchUnique(value: String?) {
        val text = value ?: application.getString(R.string.default_error_message)
        val timestamp = System.currentTimeMillis()

        if (text == message && timestamp - lastTimestamp < MIN_INTERVAL) {
            return
        }

        Toast.makeText(application, text, Toast.LENGTH_SHORT).show()

        message = text
        lastTimestamp = timestamp
    }
}