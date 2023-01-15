package com.elvitalya.droiderhandbook.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.utils.Event
import com.elvitalya.droiderhandbook.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AuthMethod {
    UNSELECTED, LOGIN, REGISTRATION
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    private val _authMethod = MutableStateFlow(AuthMethod.UNSELECTED)
    val authMethod = _authMethod.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Content)
    val viewState = _viewState.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val _navigateToMainScreen = MutableStateFlow(false)
    val navigateToMainScreen = _navigateToMainScreen.asStateFlow()

    private val _buttonAuthEnabled = MutableStateFlow(false)
    val buttonAuthEnabled = _buttonAuthEnabled.asStateFlow()


    fun onAuthMethodSelected(method: AuthMethod) {
        _authMethod.value = method
    }

    fun onEmailInputChanged(emailInput: String) {
        _email.value = emailInput
        checkButtonAuthEnabled()
    }

    fun onPassInputChanged(passwordInput: String) {
        _password.value = passwordInput
        checkButtonAuthEnabled()
    }

    private fun checkButtonAuthEnabled() {
        val enabled = _email.value.length > 5
                && _email.value.contains("@") && _password.value.length > 6
        _buttonAuthEnabled.value = enabled
    }


    fun onClickLogin() {
        viewModelScope.launch {
            _viewState.value = ViewState.Loading
            val email = _email.value
            val pass = _password.value
            if (_authMethod.value == AuthMethod.LOGIN) {
                when (val response = repository.login(email, pass)) {
                    is Event.Error -> {
                        _errorMessage.value = response.message ?: ""
                        _viewState.value = ViewState.Error
                    }
                    is Event.Loading -> _viewState.value = ViewState.Loading
                    is Event.Success -> _navigateToMainScreen.value = true
                }
            } else {
                when (val response = repository.registration(email, pass)) {
                    is Event.Error -> {
                        _errorMessage.value = response.message ?: ""
                        _viewState.value = ViewState.Error
                    }
                    is Event.Loading -> _viewState.value = ViewState.Loading
                    is Event.Success -> _navigateToMainScreen.value = true
                }
            }
        }

    }
}