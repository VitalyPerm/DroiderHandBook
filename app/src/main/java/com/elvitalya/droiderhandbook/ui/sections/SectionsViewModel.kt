package com.elvitalya.droiderhandbook.ui.sections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.utils.FireBaseHelper
import com.elvitalya.droiderhandbook.utils.ToastDispatcher
import com.elvitalya.droiderhandbook.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val toastDispatcher: ToastDispatcher
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        toastDispatcher.dispatchUnique(throwable.message)
        _viewState.value = ViewState.Error
    }

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    val viewState = _viewState.asStateFlow()

    private val allQuestions = dataRepository.getQuestionsFlow()
        .stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    init {
        if (allQuestions.value.isEmpty()) reloadQuestions()
        else _viewState.value = ViewState.Content
    }

    val javaQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.id.toString().startsWith(FireBaseHelper.javaQuestionsIds)
            }
        }

    val kotlinQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.id.toString().startsWith(FireBaseHelper.kotlinQuestionsIds)
            }
        }

    val basicQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.id.toString().startsWith(FireBaseHelper.basicQuestionsIds)
            }
        }

    val androidQuestions
        get() = allQuestions.map { list ->
            list.filter { question ->
                question.id.toString().startsWith(FireBaseHelper.androidQuestionsIds)
            }
        }


    fun reloadQuestions() {
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ViewState.Loading
            dataRepository.loadQuestions()
            _viewState.value = ViewState.Content
        }
    }

    fun updateQuestion(question: QuestionEntity) {
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ViewState.Loading
            val new = question.copy(favorite = question.favorite.not())
            dataRepository.updateQuestion(new)
            _viewState.value = ViewState.Content
        }
    }
}