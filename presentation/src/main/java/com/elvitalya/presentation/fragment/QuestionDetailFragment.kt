package com.elvitalya.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import com.elvitalya.presentation.core.FragmentKey
import com.elvitalya.presentation.core.createComposeView
import com.elvitalya.presentation.core.lookupBottomSheetBackstack
import com.elvitalya.presentation.screens.QuestionDetailsScreen
import com.elvitalya.presentation.viewmodel.QuestionDetailViewModel
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

@Parcelize
data class QuestionDetailKey(val questionId: Long) : FragmentKey() {
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