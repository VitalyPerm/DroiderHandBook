package com.elvitalya.presentation.core

import android.content.Context
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.servicesktx.lookup
import kotlin.math.roundToInt


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

fun Context.lookupBottomSheetBackstack(): Backstack {
    return Navigator.getBackstack(this).lookup(AppBottomSheetView.BACKSTACK_TAG)
}


fun Backstack.canNavigateBack(): Boolean {
    val keys = getHistory<DefaultFragmentKey>()

    // ignore PlaceholderKey if contained in history
    val size = if (keys.contains(PlaceholderKey())) keys.size - 1 else keys.size

    return size > 1
}
