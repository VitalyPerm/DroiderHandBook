package com.elvitalya.droiderhandbook.ui.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elvitalya.droiderhandbook.ui.core.FragmentKey
import com.elvitalya.droiderhandbook.ui.core.createComposeView
import com.elvitalya.droiderhandbook.ui.core.lookupBottomSheetBackstack
import com.elvitalya.droiderhandbook.ui.questiondetail.QuestionDetailKey
import com.elvitalya.droiderhandbook.utils.ViewState
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

enum class Sections {
    Java, Kotlin, Android, Basic
}

@Parcelize
data class SectionsKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = SectionsFragment()
}

@AndroidEntryPoint
class SectionsFragment : KeyedFragment() {

    val viewModel: SectionsViewModel by viewModels()

    private val bottomSheetBackstack by lazy { requireContext().lookupBottomSheetBackstack() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {
        setContent {

            val javaQuestions by viewModel.javaQuestions.collectAsState(initial = emptyList())
            val androidQuestions by viewModel.androidQuestions.collectAsState(initial = emptyList())
            val kotlinQuestions by viewModel.kotlinQuestions.collectAsState(initial = emptyList())
            val basicQuestions by viewModel.basicQuestions.collectAsState(initial = emptyList())
            val viewState by viewModel.viewState.collectAsState()

            SectionsScreen(
                javaQuestions = javaQuestions,
                androidQuestions = androidQuestions,
                kotlinQuestions = kotlinQuestions,
                basicQuestions = basicQuestions,
                viewState = viewState,
                onQuestionClick = { id -> bottomSheetBackstack.goTo(QuestionDetailKey(id)) },
                onFavoriteClick = viewModel::updateQuestion,
                onReloadClick = viewModel::reloadQuestions
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getQuestions()
    }
}