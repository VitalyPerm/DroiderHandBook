package com.elvitalya.presentation.ui.sections

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.entity.QuestionsType
import com.elvitalya.domain.toastdispatcher.ToastDispatcher
import com.elvitalya.domain.usecases.GetAllUseCase
import com.elvitalya.domain.usecases.LoadAllUseCase
import com.elvitalya.domain.usecases.UpdateQuestionUseCase
import com.elvitalya.presentation.core.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class SectionsViewModel(
    private val toastDispatcher: ToastDispatcher,
    getAllUseCase: GetAllUseCase,
    private val loadAllUseCase: LoadAllUseCase,
    private val updateQuestionUseCase: UpdateQuestionUseCase
) : ViewModel() {

    private var initialLoaded = false

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        viewState = ViewState.Error
    }
    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set

    var javaQuestions by mutableStateOf<List<Question>>(emptyList())
        private set
    var kotlinQuestions by mutableStateOf<List<Question>>(emptyList())
        private set
    var androidQuestions by mutableStateOf<List<Question>>(emptyList())
        private set
    var coroutineQuestions by mutableStateOf<List<Question>>(emptyList())
        private set

    init {
        getAllUseCase()
            .onEach { questions ->
                if (questions.isEmpty()) {
                    if (initialLoaded.not()) {
                        initialLoaded = true
                        reloadQuestions()
                    } else viewState = ViewState.Empty
                } else {
                    javaQuestions = questions.filter { question ->
                        question.type == QuestionsType.Java
                    }

                    kotlinQuestions = questions.filter { question ->
                        question.type == QuestionsType.Kotlin
                    }

                    androidQuestions = questions.filter { question ->
                        question.type == QuestionsType.Java
                    }

                    coroutineQuestions = questions.filter { question ->
                        question.type == QuestionsType.Java
                    }
                    viewState = ViewState.Content
                }
            }.launchIn(viewModelScope)
    }


    fun reloadQuestions() {
        viewModelScope.launch(exceptionHandler) {
            viewState = ViewState.Loading
            loadAllUseCase()
            viewState = ViewState.Content
        }
    }

    fun updateQuestion(question: Question) {
        viewModelScope.launch(exceptionHandler) {
            viewState = ViewState.Loading
            val new = question.copy(isFavorite = question.isFavorite.not())
            updateQuestionUseCase(new)
            viewState = ViewState.Content
        }
    }
}