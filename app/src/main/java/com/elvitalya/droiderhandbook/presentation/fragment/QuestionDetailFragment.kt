package com.elvitalya.droiderhandbook.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import com.elvitalya.droiderhandbook.presentation.core.FragmentKey
import com.elvitalya.droiderhandbook.presentation.core.createComposeView
import com.elvitalya.droiderhandbook.presentation.core.lookupBottomSheetBackstack
import com.elvitalya.droiderhandbook.presentation.screens.QuestionDetailsScreen
import com.elvitalya.droiderhandbook.presentation.viewmodel.QuestionDetailViewModel
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

@Parcelize
data class QuestionDetailKey(val questionId: String) : FragmentKey() {
    override fun instantiateFragment(): Fragment = QuestionDetailFragment()
}

class QuestionDetailFragment : KeyedFragment() {

    private val viewModel by viewModel<QuestionDetailViewModel>()

    private val contentView by lazy { requireContext().lookupBottomSheetBackstack() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {
        setContent {
            val question by viewModel.question.collectAsState()
            val viewState by viewModel.viewState.collectAsState()
            QuestionDetailsScreen(
                question = question,
                viewState = viewState,
                onCloseClick = contentView::jumpToRoot
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getQuestionById(getKey<QuestionDetailKey>().questionId)
    }

}