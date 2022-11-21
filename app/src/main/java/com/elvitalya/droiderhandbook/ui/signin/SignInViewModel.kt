package com.elvitalya.droiderhandbook.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elvitalya.droiderhandbook.data.DataRepository
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG
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

    fun onClickArrow() {
        viewModelScope.launch {
            val email = screenState.value.email
            val pass = screenState.value.pass
            if (screenState.value.authMethod == SignInScreenAuthMethod.LOGIN) {
                val response = repository.login(email, pass)
                if (response == null) {
                    Log.d(TAG, "onClickArrow: login success")
                } else {
                    Log.d(TAG, "onClickArrow: login error $response")
                }
            } else {
                val response = repository.registration(email, pass)
                if (response == null) {
                    Log.d(TAG, "onClickArrow: login registration")
                } else {
                    Log.d(TAG, "onClickArrow: login registration $response")
                }
            }
        }

    }
}