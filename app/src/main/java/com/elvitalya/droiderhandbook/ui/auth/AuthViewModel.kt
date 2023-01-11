package com.elvitalya.droiderhandbook.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AuthMethod {
    UNSELECTED, LOGIN, REGISTRATION
}

data class AuthScreenState(
    val authMethod: AuthMethod = AuthMethod.UNSELECTED,
    val email: String = "",
    val pass: String = "",
    val loading: Boolean = false,
    val errorMessage: String = ""
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    val screenState = MutableStateFlow(AuthScreenState())

    fun onAuthMethodSelected(authMethod: AuthMethod) {
        screenState.value = screenState.value.copy(authMethod = authMethod)
    }

    fun onEmailInputChanged(emailInput: String) {
        screenState.value = screenState.value.copy(email = emailInput)
    }

    fun onPassInputChanged(pass: String) {
        screenState.value = screenState.value.copy(pass = pass)
    }

    fun onClickLogin() {
        viewModelScope.launch {
            val email = screenState.value.email
            val pass = screenState.value.pass
            screenState.value = screenState.value.copy(loading = true)

            if (screenState.value.authMethod == AuthMethod.LOGIN) {
                when (val response = repository.login(email, pass)) {
                    is Result.Error -> screenState.value =
                        screenState.value.copy(loading = false, errorMessage = response.message)
                    is Result.Success -> {}
                }
            } else {
                when (val response = repository.registration(email, pass)) {
                    is Result.Error -> screenState.value =
                        screenState.value.copy(loading = false, errorMessage = response.message)
                    is Result.Success -> {}
                }
            }
        }

    }
}