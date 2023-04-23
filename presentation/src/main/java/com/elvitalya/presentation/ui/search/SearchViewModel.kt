package com.elvitalya.presentation.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.toastdispatcher.ToastDispatcher
import com.elvitalya.domain.usecases.GetAllUseCase
import com.elvitalya.domain.usecases.UpdateQuestionUseCase
import com.elvitalya.presentation.core.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SearchViewModel(
    private val toastDispatcher: ToastDispatcher,
    getAllUseCase: GetAllUseCase,
    private val updateQuestionUseCase: UpdateQuestionUseCase
) : ViewModel() {

    private val allQuestions = getAllUseCase()
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5.milliseconds),
            initialValue = emptyList()
        )

    val searchInput = MutableStateFlow("")

    val correspondedQuestions = combine(allQuestions, searchInput) { questions, input ->
        if (input.isBlank()) emptyList()
        else questions.filter { question ->
            question.title.lowercase().contains(input) || question.text.lowercase()
                .contains(input)
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        viewState = ViewState.Error
    }

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    init {
        correspondedQuestions.onEach { list ->
            viewState = if (list.isEmpty()) ViewState.Empty else ViewState.Content
        }.launchIn(viewModelScope)
    }

    fun onSearchInput(input: String) {
        if (input.length > 10) return
        searchInput.value = input
    }

    fun onFavoriteClick(question: Question) {
        viewModelScope.launch(exceptionHandler) {
            viewState = ViewState.Loading
            val new = question.copy(isFavorite = question.isFavorite.not())
            updateQuestionUseCase.run(new)
            viewState = ViewState.Content
        }
    }

}