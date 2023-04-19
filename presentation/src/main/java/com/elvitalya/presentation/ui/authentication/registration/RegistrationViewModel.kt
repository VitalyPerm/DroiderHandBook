package com.elvitalya.presentation.ui.authentication.registration

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.domain.usecases.RegistrationUseCase
import com.elvitalya.presentation.core.ViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val context: Application,
    private val registrationUseCase: RegistrationUseCase
): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorMessage = throwable.message ?: ""
        viewState = ViewState.Error
    }
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set
    var viewState by mutableStateOf<ViewState>(ViewState.Content)
        private set

    var errorMessage by mutableStateOf("")
        private set

    var authButtonEnabled by mutableStateOf(false)
        private set

    fun onEmailInputChanged(emailInput: String) {
        email = emailInput
        checkButtonAuthEnabled()
    }

    fun onPassInputChanged(passwordInput: String) {
        password = passwordInput
        checkButtonAuthEnabled()
    }

    private fun checkButtonAuthEnabled() {
        authButtonEnabled = email.length > 5 && email.contains("@") && password.length > 6
    }
    fun registration() {
        viewModelScope.launch(exceptionHandler) {
            viewState = ViewState.Loading
            val result = registrationUseCase.run(email, password)
            if (result.isSuccess) {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }
        }
    }
}