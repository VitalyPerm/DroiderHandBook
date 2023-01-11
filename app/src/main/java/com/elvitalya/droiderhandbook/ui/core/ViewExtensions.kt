package com.elvitalya.droiderhandbook.ui.core

import android.content.Context
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import kotlin.math.roundToInt

/**
 * Created by uvays on 19.02.2022.
 */

fun View.roundCorners(
    radius: Float,
    dp: Boolean = true,
    left: Boolean = true,
    top: Boolean = true,
    right: Boolean = true,
    bottom: Boolean = true
) {
    val value = if (dp) radius * resources.displayMetrics.density else radius

    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            val width = view?.width ?: 0
            val height = view?.height ?: 0

            outline?.setRoundRect(
                if (left) 0 else -value.roundToInt(),
                if (top) 0 else -value.roundToInt(),
                if (right) width else (width + value).roundToInt(),
                if (bottom) height else (height + value).roundToInt(),
                value
            )
        }
    }
}


fun createComposeView(context: Context) = ComposeView(context).apply {
    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
}
