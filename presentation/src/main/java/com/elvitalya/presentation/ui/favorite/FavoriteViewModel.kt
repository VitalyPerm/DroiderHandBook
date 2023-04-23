package com.elvitalya.presentation.ui.favorite

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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoriteViewModel(
    getAllUseCase: GetAllUseCase,
    private val updateQuestionUseCase: UpdateQuestionUseCase,
    private val toastDispatcher: ToastDispatcher
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        viewState = ViewState.Error
    }

    var questions by mutableStateOf<List<Question>>(emptyList())
        private set

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    init {
        getAllUseCase()
            .onEach { questions ->
                val filtered = questions.filter { it.isFavorite }
                if (filtered.isEmpty()) viewState = ViewState.Empty
                else {
                    this.questions = filtered
                    viewState = ViewState.Content
                }
            }.launchIn(viewModelScope)
    }

    fun onFavoriteClick(question: Question) {
        viewModelScope.launch(exceptionHandler) {
            viewState = ViewState.Loading
            val new = question.copy(isFavorite = question.isFavorite.not())
            updateQuestionUseCase(new)
            viewState = ViewState.Content
        }
    }

}
