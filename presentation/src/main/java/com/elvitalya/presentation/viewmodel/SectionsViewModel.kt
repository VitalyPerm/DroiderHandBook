package com.elvitalya.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.toastdispatcher.ToastDispatcher
import com.elvitalya.domain.usecases.GetAllUseCase
import com.elvitalya.domain.usecases.LoadAllUseCase
import com.elvitalya.domain.usecases.UpdateQuestionUseCase
import com.elvitalya.presentation.core.QuestionsType
import com.elvitalya.presentation.core.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SectionsViewModel (
    private val toastDispatcher: ToastDispatcher,
    getAllUseCase: GetAllUseCase,
    private val loadAllUseCase: LoadAllUseCase,
    private val updateQuestionUseCase: UpdateQuestionUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        _viewState.value = ViewState.Error
    }

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    val viewState = _viewState.asStateFlow()

    private val allQuestions = getAllUseCase.run()
        .stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    init {
        if (allQuestions.value.isEmpty()) reloadQuestions()
        else _viewState.value = ViewState.Content
    }

    val javaQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.id.startsWith(QuestionsType.Java.prefix)
            }
        }

    val kotlinQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.id.startsWith(QuestionsType.Kotlin.prefix)
            }
        }

    val basicQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.id.startsWith(QuestionsType.Basic.prefix)
            }
        }

    val androidQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.id.startsWith(QuestionsType.Android.prefix)
            }
        }

    val coroutineQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.id.startsWith(QuestionsType.Coroutines.prefix)
            }
        }


    fun reloadQuestions() {
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ViewState.Loading
            loadAllUseCase.run()
            _viewState.value = ViewState.Content
        }
    }

    fun updateQuestion(question: com.elvitalya.domain.entity.Question) {
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ViewState.Loading
            val new = question.copy(isFavorite = question.isFavorite.not())
            updateQuestionUseCase.run(new)
            _viewState.value = ViewState.Content
        }
    }
}