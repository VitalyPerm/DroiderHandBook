package com.elvitalya.droiderhandbook.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.utils.Result
import com.elvitalya.droiderhandbook.utils.ToastDispatcher
import com.elvitalya.droiderhandbook.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val toastDispatcher: ToastDispatcher
) : ViewModel() {

    private val allQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())

    val searchInput = MutableStateFlow("")

    val correspondedQuestions = combine(allQuestions, searchInput) { questions, input ->
        if (input.isBlank()) emptyList()
        else questions.filter { question ->
            question.title.lowercase().contains(input) || question.text.lowercase()
                .contains(input)
        }
    }

    fun onSearchInput(input: String) {
        if(input.length > 10) return
        searchInput.value = input
    }

    val viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    init {
        viewModelScope.launch {
            try {
                viewState.value = ViewState.Loading
                dataRepository.getQuestionsFlow().onEach { questions ->
                    allQuestions.value = questions
                    viewState.value = ViewState.Content
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                viewState.value = ViewState.Error
                toastDispatcher.dispatchUnique(e.message)
            }
        }
    }

    fun onFavoriteClick(question: QuestionEntity) {
        viewModelScope.launch {
            val new = question.copy(favorite = question.favorite.not())
            dataRepository.updateQuestion(new).onEach { result ->
                when (result) {
                    is Result.Error -> {
                        viewState.value = ViewState.Error
                        toastDispatcher.dispatchUnique(result.message)
                    }
                    is Result.Loading -> viewState.value = ViewState.Loading
                    is Result.Success -> viewState.value = ViewState.Content
                }
            }.launchIn(viewModelScope)
        }
    }

}