package com.elvitalya.presentation.ui.question_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        viewState = ViewState.Error
    }


    fun getQuestionById(id: String?) {
        viewModelScope.launch(exceptionHandler) {
            viewState = ViewState.Loading
            _question.value = getByIdUseCase.run(id!!.toLong())
            viewState = ViewState.Content
        }
    }


    override fun onCleared() {
        super.onCleared()
        getQuestionByIdJob?.cancel()
    }

}