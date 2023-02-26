package com.elvitalya.data.toastdispatcher

import android.content.Context
import android.widget.Toast
import com.elvitalya.data.R
import com.elvitalya.domain.toastdispatcher.ToastDispatcher

class ToastDispatcherImpl(
    private val context: Context
) : ToastDispatcher {
    companion object {
        private const val MIN_INTERVAL = 3_000L
    }

    private var message: String? = null
    private var lastTimestamp: Long = 0L

    override fun dispatchUnique(value: String?) {
        val text = value ?: context.getString(R.string.default_error_message)
        val timestamp = System.currentTimeMillis()

        if (text == message && timestamp - lastTimestamp < MIN_INTERVAL) {
            return
        }

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

        message = text
        lastTimestamp = timestamp
    }
}