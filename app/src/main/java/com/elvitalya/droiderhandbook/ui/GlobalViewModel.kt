package com.elvitalya.droiderhandbook.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG
import com.elvitalya.droiderhandbook.utils.FireBaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    val javaQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val kotlinQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val basicQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val androidQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())


    val detailQuestion = MutableStateFlow<QuestionEntity?>(null)

    val loading = MutableStateFlow(true)

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
                loading.value = true
                dataRepository.loadQuestions()
                loading.value = false
            } catch (e: Exception) {
                loading.value = false
            }
        }
    }

    fun getQuestions() {
        viewModelScope.launch {
            try {
                loading.value = true
                dataRepository.getQuestions().collect { questions ->
                    if (questions.isEmpty()) reloadQuestions()
                    else {
                        javaQuestions.value =
                            questions.filter { question ->
                                question.id.toString().startsWith(FireBaseHelper.javaQuestionsIds)
                            }
                        kotlinQuestions.value =
                            questions.filter { question ->
                                question.id.toString().startsWith(FireBaseHelper.kotlinQuestionsIds)
                            }
                        basicQuestions.value =
                            questions.filter { question ->
                                question.id.toString().startsWith(FireBaseHelper.basicQuestionsIds)
                            }
                        androidQuestions.value =
                            questions.filter { question ->
                                question.id.toString()
                                    .startsWith(FireBaseHelper.androidQuestionsIds)
                            }
                        loading.value = false
                    }
                }
            } catch (e: Exception) {
                loading.value = false
                Log.d(TAG, "getQuestions: error ${e.message}")
            }

        }
    }
}