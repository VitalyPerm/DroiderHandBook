package com.elvitalya.droiderhandbook.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.utils.Result
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
class FavoriteViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val toastDispatcher: ToastDispatcher
) : ViewModel() {

    private val favoriteQuestions = MutableStateFlow<List<QuestionEntity>>(emptyList())

    val questions
        get() = favoriteQuestions.map { list ->
            list.filter { question -> question.favorite }
        }


    val viewState = MutableStateFlow<ViewState>(ViewState.Loading)

    init {
        viewModelScope.launch {
            try {
                viewState.value = ViewState.Loading
                dataRepository.getQuestionsFlow().onEach { questions ->
                    favoriteQuestions.value = questions
                    viewState.value = ViewState.Content
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                viewState.value = ViewState.Error
                toastDispatcher.dispatchUnique(e.message)
            }
        }
    }

    fun unMarkAsFavorite(question: QuestionEntity) {
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
