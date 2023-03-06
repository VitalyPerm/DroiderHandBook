package com.elvitalya.droiderhandbook.presentation.viewmodel

import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.entity.QuestionsType
import com.elvitalya.domain.usecases.GetAllUseCase
import com.elvitalya.droiderhandbook.MainCoroutineRule
import com.elvitalya.droiderhandbook.data.repository.FakeQuestionsRepository
import com.elvitalya.presentation.viewmodel.TestViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TestViewModelTest {

    private lateinit var testViewModel: TestViewModel

    private lateinit var getAllUseCase: GetAllUseCase

    private lateinit var questionsRepository: FakeQuestionsRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUpViewModel() {
        questionsRepository = FakeQuestionsRepository()
        getAllUseCase = GetAllUseCase(questionsRepository)
        val question1 = Question(
            id = 0,
            number = 0,
            title = "",
            text = "",
            picUrl = "",
            isFavorite = false,
            type = QuestionsType.Coroutines
        )

        val question2 = Question(
            id = 2,
            number = 2,
            title = "",
            text = "",
            picUrl = "",
            isFavorite = false,
            type = QuestionsType.Android
        )

        val question3 = Question(
            id = 3,
            number = 3,
            title = "",
            text = "",
            picUrl = "",
            isFavorite = false,
            type = QuestionsType.Java
        )

        questionsRepository.addQuestions(question1, question2, question3)

        testViewModel = TestViewModel(getAllUseCase)

    }


    @Test
    fun getRandomQuestion_ReturnsQuestionFromList() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())

        testViewModel.getRandomQuestion()

        val question = testViewModel.question.first()

        assertThat(question in questionsRepository.savedQuestions.value)

    }

    @Test
    fun getRandomQuestionIfEmptyList_Returns() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())

        questionsRepository.clearQuestionsList()

        testViewModel.getRandomQuestion()

        val question = testViewModel.question.first()
        assertThat(question == Question.EMPTY)
    }

}