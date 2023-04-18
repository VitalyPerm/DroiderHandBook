package com.elvitalya.presentation.ui.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.usecases.GetAllUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.random.Random

class TestViewModel(
    getAllUseCase: GetAllUseCase
) : ViewModel() {

    private val allQuestions = getAllUseCase.run().stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )

    private val questionIndex = MutableStateFlow(0)

    val question = combine(allQuestions, questionIndex) { all, index ->
        if (all.isEmpty()) Question.EMPTY else all.getOrNull(index) ?: Question.EMPTY
    }

    fun getRandomQuestion() {
        val lastIndex = allQuestions.value.lastIndex
        if (lastIndex == -1) return
        questionIndex.value = Random.nextInt(lastIndex)
    }
}
