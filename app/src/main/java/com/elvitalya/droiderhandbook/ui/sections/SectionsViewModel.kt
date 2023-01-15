package com.elvitalya.droiderhandbook.ui.sections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.utils.FireBaseHelper
import com.elvitalya.droiderhandbook.utils.Event
import com.elvitalya.droiderhandbook.utils.ToastDispatcher
import com.elvitalya.droiderhandbook.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val toastDispatcher: ToastDispatcher
) : ViewModel() {

    private val allQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())

    val viewState = MutableStateFlow<ViewState>(ViewState.Loading)

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
        dataRepository.loadQuestions().onEach { event ->
            when (event) {
                is Event.Error -> viewState.value = ViewState.Error
                is Event.Loading -> viewState.value = ViewState.Loading
                is Event.Success -> viewState.value = ViewState.Content
            }
        }.launchIn(viewModelScope)
    }

    fun getQuestions() {
        viewModelScope.launch {
            try {
                viewState.value = ViewState.Loading
                dataRepository.getQuestionsFlow().collect { questions ->
                    if (questions.isEmpty()) reloadQuestions()
                    else {
                        allQuestions.value = questions
                        viewState.value = ViewState.Content
                    }
                }
            } catch (e: Exception) {
                viewState.value = ViewState.Error
            }

        }
    }

    fun updateQuestion(question: QuestionEntity) {
        viewModelScope.launch {
            val new = question.copy(favorite = question.favorite.not())
            dataRepository.updateQuestion(new).onEach { result ->
                when (result) {
                    is Event.Error -> {
                        viewState.value = ViewState.Error
                        toastDispatcher.dispatchUnique(result.message)
                    }
                    is Event.Loading -> viewState.value = ViewState.Loading
                    is Event.Success -> viewState.value = ViewState.Content
                }
            }.launchIn(viewModelScope)
        }
    }
}