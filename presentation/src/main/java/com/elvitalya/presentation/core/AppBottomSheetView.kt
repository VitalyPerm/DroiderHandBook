package com.elvitalya.presentation.core

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentContainerView
import com.elvitalya.presentation.R
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import dev.chrisbanes.insetter.applyInsetter

class AppBottomSheetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val BACKSTACK_TAG = "BOTTOM_SHEET_BACKSTACK"

        fun createBackstack(): Backstack {
            val backstack = Backstack()
            backstack.setScopedServices(DefaultServiceProvider())
            backstack.setup(History.of(PlaceholderKey()))

            return backstack
        }
    }

    private val backstack by lazy {
        getContext().lookupBottomSheetBackstack()
    }

    private val screenHeight = resources.displayMetrics.heightPixels

    private val viewBackground: View
    private val viewFragmentContainer: FragmentContainerView

    private var showAnimator: ViewPropertyAnimator? = null
    private var hideAnimator: ViewPropertyAnimator? = null

    init {
        val view = inflate(context, R.layout.bottom_sheet_app, this)

        viewBackground = view.findViewById(R.id.viewBackground)
        viewFragmentContainer = view.findViewById(R.id.fragmentContainer)

        layoutTransition = LayoutTransition()

        viewBackground.applyInsetter {
            type(statusBars = true) { padding(top = true) }
        }

        viewFragmentContainer.roundCorners(16f, bottom = false)
        viewFragmentContainer.applyInsetter {
            type(statusBars = true) { margin(top = true) }
        }
    }

    private fun show() {
        isVisible = true
        viewFragmentContainer.translationY = screenHeight.toFloat()

        viewFragmentContainer.post {
            viewFragmentContainer.isVisible = true

            showAnimator?.cancel()

            showAnimator = viewFragmentContainer
                .animate()
                .translationYBy(-screenHeight.toFloat())
                .setDuration(250L)
                .setInterpolator(AccelerateDecelerateInterpolator())

            showAnimator?.start()
        }
    }

    private fun hide() {
        hideAnimator?.cancel()

        hideAnimator = viewFragmentContainer
            .animate()
            .translationYBy(screenHeight.toFloat())
            .setDuration(250L)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                isVisible = false
                viewFragmentContainer.isVisible = false
            }

        hideAnimator?.start()
    }

    fun attachStateChanger() {
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        val stateChanger = AppStateChanger(fragmentManager, viewFragmentContainer.id)

        backstack.reattachStateChanger()

        backstack.setStateChanger(SimpleStateChanger { stateChange ->
            val newKeySize = stateChange.getNewKeys<DefaultFragmentKey>().size

            if (newKeySize > 1) {
                if (!isVisible) {
                    show()
                }
            } else {
                if (isVisible) {
                    hide()
                }
            }

            stateChanger.handleStateChange(stateChange)
        })
    }
}