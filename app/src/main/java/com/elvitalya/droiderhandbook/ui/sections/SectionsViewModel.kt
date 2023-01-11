package com.elvitalya.droiderhandbook.ui.sections

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG
import com.elvitalya.droiderhandbook.utils.FireBaseHelper
import com.elvitalya.droiderhandbook.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionsViewModel @Inject constructor(
    private val dataRepository: DataRepository
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

    val favoriteQuestions
        get() = allQuestions.map { list ->
            list.filter { question -> question.favorite }
        }


    val detailQuestion = MutableStateFlow<QuestionEntity?>(null)

    fun getQuestionById(id: Int) {
        viewModelScope.launch {
            try {
                val question = dataRepository.getQuestionById(id)
                detailQuestion.value = question
            } catch (e: Exception) {

            }
        }
    }


    fun reloadQuestions() {
        viewModelScope.launch {
            try {
                viewState.value = ViewState.Loading
                dataRepository.loadQuestions()
                viewState.value = ViewState.Content
            } catch (e: Exception) {
                viewState.value = ViewState.Error
            }
        }
    }

    fun getQuestions() {
        Log.d(TAG, "getQuestions: called")
        viewModelScope.launch {
            try {
                viewState.value = ViewState.Loading
                dataRepository.getQuestions().collect { questions ->
                    if (questions.isEmpty()) reloadQuestions()
                    else {
                        Log.d(TAG, "getQuestions: else")
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
            dataRepository.updateQuestion(question)
        }
    }
}