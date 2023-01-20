package com.elvitalya.droiderhandbook.ui.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TestViewModel @Inject constructor(
    dataRepository: DataRepository
) : ViewModel() {

    private val allQuestions = dataRepository.getQuestionsFlow().stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )

    private val questionIndex = MutableStateFlow(0)

    val question = combine(allQuestions, questionIndex) { all, index ->
        if (all.isEmpty()) QuestionEntity.EMPTY else all[index]
    }

    fun getRandomQuestions() {
        val lastIndex = allQuestions.value.lastIndex
        if (lastIndex == -1) return
        questionIndex.value = Random.nextInt(lastIndex)
    }
}
