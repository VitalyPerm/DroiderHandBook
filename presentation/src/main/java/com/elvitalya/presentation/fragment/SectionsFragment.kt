package com.elvitalya.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import com.elvitalya.presentation.R
import com.elvitalya.presentation.core.FragmentKey
import com.elvitalya.presentation.core.createComposeView
import com.elvitalya.presentation.core.lookupBottomSheetBackstack
import com.elvitalya.presentation.screens.SectionsScreen
import com.elvitalya.presentation.viewmodel.SectionsViewModel
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

enum class Sections(@StringRes val nameRes: Int) {
    Java(R.string.section_java),
    Kotlin(R.string.section_kotlin),
    Android(R.string.section_android),
    Basic(R.string.section_basic),
    Coroutines(R.string.section_coroutines)
}

@Parcelize
data class SectionsKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = SectionsFragment()
}

class SectionsFragment : KeyedFragment() {

    private val viewModel by viewModel<SectionsViewModel>()

    private val contentView by lazy { requireContext().lookupBottomSheetBackstack() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {
        setContent {

            val javaQuestions by viewModel.javaQuestions.collectAsState(emptyList())
            val androidQuestions by viewModel.androidQuestions.collectAsState(emptyList())
            val kotlinQuestions by viewModel.kotlinQuestions.collectAsState(emptyList())
            val basicQuestions by viewModel.basicQuestions.collectAsState(emptyList())
            val coroutinesQuestions by viewModel.coroutineQuestions.collectAsState(emptyList())
            val viewState by viewModel.viewState.collectAsState()

            SectionsScreen(
                javaQuestions = javaQuestions,
                androidQuestions = androidQuestions,
                kotlinQuestions = kotlinQuestions,
                basicQuestions = basicQuestions,
                coroutinesQuestions = coroutinesQuestions,
                viewState = viewState,
                onQuestionClick = ::navigateToDetails,
                onFavoriteClick = viewModel::updateQuestion,
                onReloadClick = viewModel::reloadQuestions
            )
        }
    }

    private fun navigateToDetails(questionId: String) {
        contentView.goTo(QuestionDetailKey(questionId))
    }
}