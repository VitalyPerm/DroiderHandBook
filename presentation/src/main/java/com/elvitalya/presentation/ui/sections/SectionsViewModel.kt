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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SectionsViewModel(
    private val toastDispatcher: ToastDispatcher,
    getAllUseCase: GetAllUseCase,
    private val loadAllUseCase: LoadAllUseCase,
    private val updateQuestionUseCase: UpdateQuestionUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        viewState = ViewState.Error
    }
    var viewState by mutableStateOf<ViewState>(ViewState.Loading)
        private set


    private val allQuestions = getAllUseCase.run()
        .stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    init {
        viewModelScope.launch {
            val needInitialLoad = allQuestions.debounce(300).first().isEmpty()
            if (needInitialLoad) reloadQuestions() else viewState = ViewState.Content
        }
    }

    val javaQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.type == QuestionsType.Java
            }
        }

    val kotlinQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.type == QuestionsType.Kotlin
            }
        }

    val androidQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.type == QuestionsType.Android
            }
        }

    val coroutineQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.type == QuestionsType.Coroutines
            }
        }


    fun reloadQuestions() {
        viewModelScope.launch(exceptionHandler) {
            viewState = ViewState.Loading
            loadAllUseCase.run()
            viewState = ViewState.Content
        }
    }

    fun updateQuestion(question: Question) {
        viewModelScope.launch(exceptionHandler) {
            viewState = ViewState.Loading
            val new = question.copy(isFavorite = question.isFavorite.not())
            updateQuestionUseCase.run(new)
            viewState = ViewState.Content
        }
    }
}