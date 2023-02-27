package com.elvitalya.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.usecases.LoginUseCase
import com.elvitalya.domain.usecases.RegistrationUseCase
import com.elvitalya.presentation.core.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AuthMethod {
    UNSELECTED, LOGIN, REGISTRATION
}

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _errorMessage.value = throwable.message ?: ""
        _viewState.value = ViewState.Error
    }

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
        val enabled =
            _email.value.length > 5 && _email.value.contains("@") && _password.value.length > 6
        _buttonAuthEnabled.value = enabled
    }


    fun onClickLogin() {
        viewModelScope.launch(exceptionHandler) {
            _viewState.value = ViewState.Loading
            val email = _email.value
            val pass = _password.value
            val authResult =
                if (_authMethod.value == AuthMethod.LOGIN) loginUseCase.run(email, pass)
                else registrationUseCase.run(email, pass)
            if (authResult.isSuccess) _navigateToMainScreen.value = true
        }

    }
}