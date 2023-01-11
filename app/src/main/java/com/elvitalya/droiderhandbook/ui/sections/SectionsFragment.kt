package com.elvitalya.droiderhandbook.ui.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elvitalya.droiderhandbook.ui.core.FragmentKey
import com.elvitalya.droiderhandbook.ui.core.createComposeView
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.parcelize.Parcelize

enum class Sections {
    Java, Kotlin, Android, Basic
}

@Parcelize
data class SectionsKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = SectionsFragment()
}

class SectionsFragment : KeyedFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {
        setContent {
            SectionsScreen(
                javaQuestions = emptyList(),
                androidQuestions = emptyList(),
                kotlinQuestions = emptyList(),
                basicQuestions = emptyList(),
                loading = false,
                onQuestionClick = {},
                onFavoriteClick = {}
            )
        }
    }
}