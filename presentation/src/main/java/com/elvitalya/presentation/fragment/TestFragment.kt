package com.elvitalya.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import com.elvitalya.domain.entity.Question
import com.elvitalya.presentation.core.FragmentKey
import com.elvitalya.presentation.core.createComposeView
import com.elvitalya.presentation.screens.TestScreen
import com.elvitalya.presentation.viewmodel.TestViewModel
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

@Parcelize
data class TestKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = TestFragment()
}

class TestFragment : KeyedFragment() {

    private val viewModel  by viewModel<TestViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {
        setContent {
            val question by viewModel.question.collectAsState(initial = Question.EMPTY)
            TestScreen(
                question = question,
                onNextQuestionClick = viewModel::getRandomQuestion
            )
        }
    }
}