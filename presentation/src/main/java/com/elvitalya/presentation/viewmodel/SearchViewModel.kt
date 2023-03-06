package com.elvitalya.presentation.viewmodel

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
import org.jetbrains.annotations.TestOnly

class SearchViewModel(
    private val toastDispatcher: ToastDispatcher,
    getAllUseCase: GetAllUseCase,
    private val updateQuestionUseCase: UpdateQuestionUseCase
) : ViewModel() {

    private val allQuestions = getAllUseCase.run()
        .stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

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
        _viewState.value = ViewState.Error
    }

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Content)
    val viewState = _viewState.asStateFlow()

    init {
        correspondedQuestions.onEach { list ->
            _viewState.value = if (list.isEmpty()) ViewState.Empty else ViewState.Content
        }.launchIn(viewModelScope)
    }

    fun onSearchInput(input: String) {
        if (input.length > 10) return
        searchInput.value = input
    }

    fun onFavoriteClick(question: Question) {
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ViewState.Loading
            val new = question.copy(isFavorite = question.isFavorite.not())
            updateQuestionUseCase.run(new)
            _viewState.value = ViewState.Content
        }
    }

}