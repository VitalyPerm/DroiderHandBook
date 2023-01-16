package com.elvitalya.droiderhandbook.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.core.FragmentKey
import com.elvitalya.droiderhandbook.ui.core.createComposeView
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = TestFragment()
}

@AndroidEntryPoint
class TestFragment : KeyedFragment() {

    private val viewModel: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {
        setContent {
            val question by viewModel.question.collectAsState(initial = QuestionEntity.EMPTY)
            TestScreen(
                question = question,
                onNextQuestionClick = viewModel::getRandomQuestions
            )
        }
    }
}