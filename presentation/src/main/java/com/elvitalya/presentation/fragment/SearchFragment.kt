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
import com.elvitalya.presentation.screens.SearchScreen
import com.elvitalya.presentation.viewmodel.SearchViewModel
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

@Parcelize
data class SearchKey(val placeholder: Int = 0) : FragmentKey() {
    override fun instantiateFragment(): Fragment = SearchFragment()
}

class SearchFragment : KeyedFragment() {

    private val viewModel  by viewModel<SearchViewModel>()

    private val contentView by lazy { requireContext().lookupBottomSheetBackstack() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeView(requireContext()).apply {
        setContent {
            val viewState by viewModel.viewState.collectAsState()
            val searchInput by viewModel.searchInput.collectAsState()
            val questions by viewModel.correspondedQuestions.collectAsState(emptyList())

            SearchScreen(
                viewState = viewState,
                searchInput = searchInput,
                onSearchInput = viewModel::onSearchInput,
                questions = questions,
                onFavoriteClick = viewModel::onFavoriteClick,
                onQuestionClick = ::goToDetailsScreen
            )
        }
    }

    private fun goToDetailsScreen(id: String) {
        contentView.goTo(QuestionDetailKey(id))
    }
}