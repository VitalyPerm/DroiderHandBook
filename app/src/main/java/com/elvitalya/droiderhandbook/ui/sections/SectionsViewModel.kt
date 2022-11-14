package com.elvitalya.droiderhandbook.ui.sections

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG
import com.elvitalya.droiderhandbook.utils.FireBaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionsViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    val sectionsList = MutableStateFlow<List<String>>(emptyList())

    val javaQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val kotlinQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val basicQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val androidQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())

    fun getSections() {
        FireBaseHelper.mainSection
            .get()
            .addOnSuccessListener { snapShot ->
                sectionsList.value = snapShot.documents.map { documentSnapshot ->
                    documentSnapshot.data?.values?.first().toString()
                }

            }
    }

    fun getQuestions() {
        viewModelScope.launch {
            try {
                dataRepository.getQuestions().collect { list ->
                    if (list.isEmpty()) dataRepository.loadQuestions()
                    else {
                        Log.d(TAG, "getQuestions: list - $list")
                        javaQuestions.value =
                            list.filter { question -> question.id in FireBaseHelper.javaQuestionsIds }
                        kotlinQuestions.value =
                            list.filter { question -> question.id in FireBaseHelper.kotlinQuestionsIds }
                        basicQuestions.value =
                            list.filter { question -> question.id in FireBaseHelper.basicQuestionsIds }
                        androidQuestions.value =
                            list.filter { question -> question.id in FireBaseHelper.androidQuestionsIds }
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "getQuestions: error ${e.message}")
            }

        }
    }
}