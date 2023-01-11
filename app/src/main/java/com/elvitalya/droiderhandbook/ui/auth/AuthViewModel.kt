package com.elvitalya.droiderhandbook.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.utils.Result
import com.elvitalya.droiderhandbook.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AuthMethod {
    UNSELECTED, LOGIN, REGISTRATION
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    val authMethod = MutableStateFlow(AuthMethod.UNSELECTED)
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val viewState = MutableStateFlow<ViewState>(ViewState.Content)
    val errorMessage = MutableStateFlow("")
    val navigateToMainScreen = MutableStateFlow(false)


    fun onAuthMethodSelected(method: AuthMethod) {
        authMethod.value = method
    }

    fun onEmailInputChanged(emailInput: String) {
        email.value = emailInput
    }

    fun onPassInputChanged(passwordInput: String) {
        password.value = passwordInput
    }

    fun onClickLogin() {
        viewModelScope.launch {
            val email = email.value
            val pass = password.value

            if (authMethod.value == AuthMethod.LOGIN) {
                when (val response = repository.login(email, pass)) {
                    is Result.Error -> {
                        errorMessage.value = response.message ?: ""
                        viewState.value = ViewState.Error
                    }
                    is Result.Loading -> viewState.value = ViewState.Loading
                    is Result.Success -> navigateToMainScreen.value = true
                }
            } else {
                when (val response = repository.registration(email, pass)) {
                    is Result.Error -> {
                        errorMessage.value = response.message ?: ""
                        viewState.value = ViewState.Error
                    }
                    is Result.Loading -> viewState.value = ViewState.Loading
                    is Result.Success -> navigateToMainScreen.value = true
                }
            }
        }

    }
}