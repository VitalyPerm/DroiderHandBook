package com.elvitalya.droiderhandbook.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SignInScreenAuthMethod {
    UNSELECTED, LOGIN, REGISTRATION
}

data class SignInScreenState(
    val authMethod: SignInScreenAuthMethod = SignInScreenAuthMethod.UNSELECTED,
    val email: String = "",
    val pass: String = "",
    val loading: Boolean = false,
    val errorMessage: String = ""
)

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    val screenState = MutableStateFlow(SignInScreenState())

    fun onAuthMethodSelected(authMethod: SignInScreenAuthMethod) {
        screenState.value = screenState.value.copy(authMethod = authMethod)
    }

    fun onEmailInputChanged(emailInput: String) {
        screenState.value = screenState.value.copy(email = emailInput)
    }

    fun onPassInputChanged(pass: String) {
        screenState.value = screenState.value.copy(pass = pass)
    }

    fun onClickArrow(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val email = screenState.value.email
            val pass = screenState.value.pass
            screenState.value = screenState.value.copy(loading = true)

            if (screenState.value.authMethod == SignInScreenAuthMethod.LOGIN) {
                when (val response = repository.login(email, pass)) {
                    is Result.Error -> screenState.value =
                        screenState.value.copy(loading = false, errorMessage = response.message)
                    is Result.Success -> onSuccess()
                }
            } else {
                when (val response = repository.registration(email, pass)) {
                    is Result.Error -> screenState.value =
                        screenState.value.copy(loading = false, errorMessage = response.message)
                    is Result.Success -> onSuccess()
                }
            }
        }

    }
}