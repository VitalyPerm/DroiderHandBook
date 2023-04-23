package com.elvitalya.presentation.ui.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.usecases.GetAllUseCase
import com.elvitalya.presentation.core.ViewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TestViewModel(
    getAllUseCase: GetAllUseCase
) : ViewModel() {

    private var allQuestions = emptyList<Question>()

    var question by mutableStateOf(Question.EMPTY)
        private set

    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    init {
        viewModelScope.launch {
            getAllUseCase()
                .onEach { questions ->
                    if (questions.isEmpty()) viewState = ViewState.Empty
                    else {
                        allQuestions = questions
                        getRandomQuestion()
                        viewState = ViewState.Content
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun getRandomQuestion() {
        allQuestions.run { if (isNotEmpty()) question = random() }
    }
}
