package com.elvitalya.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.toastdispatcher.ToastDispatcher
import com.elvitalya.domain.usecases.GetByIdUseCase
import com.elvitalya.presentation.core.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuestionDetailViewModel(
    private val getByIdUseCase: GetByIdUseCase,
    private val toastDispatcher: ToastDispatcher
) : ViewModel() {

    private var getQuestionByIdJob: Job? = null

    private val _question = MutableStateFlow<Question?>(null)
    val question = _question.asStateFlow()

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState = _viewState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        _viewState.value = ViewState.Error
    }


    fun getQuestionById(id: Long) {
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ViewState.Loading
            _question.value = getByIdUseCase.run(id)
            _viewState.value = ViewState.Content
        }
    }


    override fun onCleared() {
        super.onCleared()
        getQuestionByIdJob?.cancel()
    }

}