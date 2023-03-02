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
import com.elvitalya.presentation.screens.FavoriteScreen
import com.elvitalya.presentation.viewmodel.FavoriteViewModel
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

@Parcelize
data class FavoriteKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = FavoriteFragment()
}

class FavoriteFragment : KeyedFragment() {

    private val viewModel  by viewModel<FavoriteViewModel>()

    private val contentView by lazy { requireContext().lookupBottomSheetBackstack() }
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
                onFavoriteClick = viewModel::onFavoriteClick,
                onQuestionClick = ::goToDetailsScreen,
                viewState = viewState
            )
        }
    }

    private fun goToDetailsScreen(id: Long) {
        contentView.goTo(QuestionDetailKey(id))
    }
}