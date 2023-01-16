package com.elvitalya.droiderhandbook.ui.favorite

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
class FavoriteViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val toastDispatcher: ToastDispatcher
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        _viewState.value = ViewState.Error
    }

    private val allQuestions = dataRepository.getQuestionsFlow()
        .stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    val questions
        get() = allQuestions.map { list ->
            list.filter { question -> question.favorite }
        }


    private val _viewState = MutableStateFlow<ViewState>(ViewState.Content)
    val viewState = _viewState.asStateFlow()

    fun onFavoriteClick(question: QuestionEntity) {
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ViewState.Loading
            val new = question.copy(favorite = question.favorite.not())
            dataRepository.updateQuestion(new)
            _viewState.value = ViewState.Content
        }
    }

}
