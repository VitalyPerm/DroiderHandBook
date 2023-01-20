package com.elvitalya.droiderhandbook.ui.questiondetail

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
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionDetailKey(val questionId: String) : FragmentKey() {
    override fun instantiateFragment(): Fragment = QuestionDetailFragment()
}

@AndroidEntryPoint
class QuestionDetailFragment : KeyedFragment() {

    private val viewModel: QuestionDetailViewModel by viewModels()

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
                onCloseClick = { contentView.jumpToRoot() }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getQuestionById(getKey<QuestionDetailKey>().questionId)
    }

}