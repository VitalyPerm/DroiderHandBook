package com.elvitalya.droiderhandbook.ui.questiondetail

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.App
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.data.model.QuestionEntity
import com.elvitalya.droiderhandbook.utils.Result
import com.elvitalya.droiderhandbook.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionDetailViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val app: Application
) : ViewModel() {

    private var getQuestionByIdJob: Job? = null

    private val _question = MutableStateFlow<QuestionEntity?>(null)
    val question = _question.asStateFlow()

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState = _viewState.asStateFlow()


    fun getQuestionById(id: Long) {
        dataRepository.getQuestionById(id).onEach { result ->
            when (result) {
                is Result.Error -> {
                    _viewState.value = ViewState.Error
                    Toast.makeText(app, result.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> _viewState.value = ViewState.Loading
                is Result.Success -> {
                    _question.value = result.data
                    _viewState.value = ViewState.Content
                }
            }
        }.launchIn(viewModelScope)
    }


    override fun onCleared() {
        super.onCleared()
        getQuestionByIdJob?.cancel()
    }

}