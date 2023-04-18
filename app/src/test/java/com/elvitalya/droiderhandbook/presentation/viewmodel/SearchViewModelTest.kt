package com.elvitalya.droiderhandbook.presentation.viewmodel

import com.elvitalya.domain.entity.Question
import com.elvitalya.domain.entity.QuestionsType
import com.elvitalya.domain.usecases.GetAllUseCase
import com.elvitalya.domain.usecases.UpdateQuestionUseCase
import com.elvitalya.droiderhandbook.MainCoroutineRule
import com.elvitalya.droiderhandbook.data.FakeToastDispatcher
import com.elvitalya.droiderhandbook.data.repository.FakeQuestionsRepository
import com.elvitalya.presentation.ui.search.SearchViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var toastDispatcher: FakeToastDispatcher

    private lateinit var getAllUseCase: GetAllUseCase

    private lateinit var updateQuestionUseCase: UpdateQuestionUseCase

    private lateinit var questionsRepository: FakeQuestionsRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUpViewModel() {
        questionsRepository = FakeQuestionsRepository()
        getAllUseCase = GetAllUseCase(questionsRepository)
        updateQuestionUseCase = UpdateQuestionUseCase(questionsRepository)
        val question1 = Question(
            id = 1,
            number = 0,
            title = "fakeTTT",
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

        toastDispatcher = FakeToastDispatcher()

        searchViewModel = SearchViewModel(
            toastDispatcher = toastDispatcher,
            getAllUseCase = getAllUseCase,
            updateQuestionUseCase = updateQuestionUseCase
        )

    }

    @Test
    fun searchInputMoreThanTenCharIgnored() {
        searchViewModel.onSearchInput("12345678910")
        val input = searchViewModel.searchInput.value
        assert(input.isBlank())
    }


    @Test
    fun searchInputSearchesQuestions() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        val input = "fakeTTT"
        searchViewModel.onSearchInput(input)
        val result = searchViewModel.correspondedQuestions.first()
        val questionThatContainsInput =
            questionsRepository.savedQuestions.value.find { it.title == input }
        assertThat(questionThatContainsInput in result)
    }

    @Test
    fun searchInputDontCareUpperCase() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        val input = "FaKeTtT"
        searchViewModel.onSearchInput(input)
        val result = searchViewModel.correspondedQuestions.first()
        val questionThatContainsInput =
            questionsRepository.savedQuestions.value.find { it.title == input }
        assertThat(questionThatContainsInput in result)
    }

    @Test
    fun searchInputReturnsEmptyListIfNoResultFound() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        val input = "NOTHING"
        searchViewModel.onSearchInput(input)
        val result = searchViewModel.correspondedQuestions.first()
        assert(result.isEmpty())
    }

    @Test
    fun onFavoriteClickMarksAsFavorite_loadingToggles() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        val idToMarkAsFavorite = 1L
        val questionToMarkAsFavorite = questionsRepository.savedQuestions.value.find { it.id == idToMarkAsFavorite }!!
        assertThat(questionToMarkAsFavorite.isFavorite).isFalse()

        searchViewModel.onFavoriteClick(questionToMarkAsFavorite)

        advanceUntilIdle()

        val questionAfterMarked = questionsRepository.savedQuestions.value.find { it.id == idToMarkAsFavorite }!!
        assertThat(questionAfterMarked.isFavorite).isTrue()
    }


}