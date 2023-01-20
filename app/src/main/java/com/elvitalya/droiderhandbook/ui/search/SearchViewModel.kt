package com.elvitalya.droiderhandbook.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.utils.ToastDispatcher
import com.elvitalya.droiderhandbook.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val toastDispatcher: ToastDispatcher
) : ViewModel() {

    private val allQuestions = dataRepository.getQuestionsFlow()
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

    fun onFavoriteClick(question: QuestionEntity) {
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ViewState.Loading
            val new = question.copy(favorite = question.favorite.not())
            dataRepository.updateQuestion(new)
            _viewState.value = ViewState.Content
        }
    }

}