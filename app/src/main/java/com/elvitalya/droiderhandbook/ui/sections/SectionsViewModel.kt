package com.elvitalya.droiderhandbook.ui.sections

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG
import com.elvitalya.droiderhandbook.utils.FireBaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionsViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    val javaQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val kotlinQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val basicQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val androidQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())

    val loading = MutableStateFlow(true)

    fun getQuestions() {
        viewModelScope.launch {
            try {
                loading.value = true
                dataRepository.getQuestions().collect { questions ->
                    if (questions.isEmpty()) dataRepository.loadQuestions()
                    else {
                        javaQuestions.value =
                            questions.filter { question -> question.id in FireBaseHelper.javaQuestionsIds }
                        kotlinQuestions.value =
                            questions.filter { question -> question.id in FireBaseHelper.kotlinQuestionsIds }
                        basicQuestions.value =
                            questions.filter { question -> question.id in FireBaseHelper.basicQuestionsIds }
                        androidQuestions.value =
                            questions.filter { question -> question.id in FireBaseHelper.androidQuestionsIds }
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