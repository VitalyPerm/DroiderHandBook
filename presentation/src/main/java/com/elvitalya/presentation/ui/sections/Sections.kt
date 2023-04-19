package com.elvitalya.presentation.ui.sections

import androidx.annotation.StringRes
import com.elvitalya.presentation.R

enum class Sections(@StringRes val nameRes: Int) {
    Java(R.string.section_java),
    Kotlin(R.string.section_kotlin),
    Android(R.string.section_android),
    Coroutines(R.string.section_coroutines)
}