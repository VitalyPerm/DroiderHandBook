package com.elvitalya.droiderhandbook.ui.favorite

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
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoriteKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = FavoriteFragment()
}

@AndroidEntryPoint
class FavoriteFragment : KeyedFragment() {

    private val viewModel: FavoriteViewModel by viewModels()

    private val bottomSheetBackstack by lazy { requireContext().lookupBottomSheetBackstack() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {

        setContent {
            val questions by viewModel.questions.collectAsState(emptyList())
            val viewState by viewModel.viewState.collectAsState()
            FavoriteScreen(
                questions = questions,
                onFavoriteClick = viewModel::unMarkAsFavorite,
                onQuestionClick = { id -> bottomSheetBackstack.goTo(QuestionDetailKey(id)) },
                viewState = viewState
            )
        }
    }
}