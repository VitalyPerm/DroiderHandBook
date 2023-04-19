package com.elvitalya.presentation.ui.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.repository.QuestionsRepository
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
    private val toastDispatcher: ToastDispatcher,
    private val questionsRepository: QuestionsRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        viewState = ViewState.Error
    }

    private val allQuestions = getAllUseCase.run()
        .stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    val questions
        get() = allQuestions.map { list ->
            list.filter { question -> question.isFavorite }
        }


    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    fun onFavoriteClick(question: com.elvitalya.domain.entity.Question) {
        viewModelScope.launch(exceptionHandler) {
            viewState = ViewState.Loading
            val new = question.copy(isFavorite = question.isFavorite.not())
            updateQuestionUseCase.run(new)
            viewState = ViewState.Content
        }
    }

    init {
        questions.onEach { list ->
            viewState = if (list.isEmpty()) ViewState.Empty else ViewState.Content
        }.launchIn(viewModelScope)
    }

}
