package com.elvitalya.presentation.ui.question_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.toastdispatcher.ToastDispatcher
import com.elvitalya.domain.usecases.GetQuestionByIdUseCase
import com.elvitalya.presentation.core.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class QuestionDetailViewModel(
    private val getQuestionByIdUseCase: GetQuestionByIdUseCase,
    private val toastDispatcher: ToastDispatcher
) : ViewModel() {

    private var getQuestionByIdJob: Job? = null

    var question by mutableStateOf(Question.EMPTY)
        private set

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        viewState = ViewState.Error
    }

    fun getQuestionById(id: String?) {
        getQuestionByIdJob = viewModelScope.launch(exceptionHandler) {
            viewState = ViewState.Loading
            question = getQuestionByIdUseCase(id!!.toLong())
            viewState = ViewState.Content
        }
    }

}