package com.elvitalya.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.toastdispatcher.ToastDispatcher
import com.elvitalya.domain.usecases.GetAllUseCase
import com.elvitalya.domain.usecases.UpdateQuestionUseCase
import com.elvitalya.presentation.core.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel(
    getAllUseCase: GetAllUseCase,
    private val updateQuestionUseCase: UpdateQuestionUseCase,
    private val toastDispatcher: ToastDispatcher
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        _viewState.value = ViewState.Error
    }

    private val allQuestions = getAllUseCase.run()
        .stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    val questions
        get() = allQuestions.map { list ->
            list.filter { question -> question.isFavorite }
        }


    private val _viewState = MutableStateFlow<ViewState>(ViewState.Content)
    val viewState = _viewState.asStateFlow()

    fun onFavoriteClick(question: com.elvitalya.domain.entity.Question) {
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ViewState.Loading
            val new = question.copy(isFavorite = question.isFavorite.not())
            updateQuestionUseCase.run(new)
            _viewState.value = ViewState.Content
        }
    }

    init {
        questions.onEach { list ->
            _viewState.value = if (list.isEmpty()) ViewState.Empty else ViewState.Content
        }.launchIn(viewModelScope)
    }

}
