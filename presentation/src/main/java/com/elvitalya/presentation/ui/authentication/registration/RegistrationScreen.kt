package com.elvitalya.presentation.ui.authentication.registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elvitalya.presentation.R
import com.elvitalya.presentation.core.AppBar
import com.elvitalya.presentation.core.LoadingBanner
import com.elvitalya.presentation.core.ViewState
import com.elvitalya.presentation.theme.white
import com.elvitalya.presentation.ui.authentication.AuthErrorCard
import com.elvitalya.presentation.ui.authentication.LoginPassInput
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = koinViewModel()
) {
    Screen(
        viewState = viewModel.viewState,
        email = viewModel.email,
        onEmailInputChanged = viewModel::onEmailInputChanged,
        password = viewModel.password,
        onPassInputChanged = viewModel::onPassInputChanged,
        errorMessage = viewModel.errorMessage,
        onAuthClick = viewModel::registration,
        authButtonEnabled = viewModel.authButtonEnabled
    )
}


@Composable
private fun Screen(
    viewState: ViewState,
    email: String,
    onEmailInputChanged: (String) -> Unit,
    password: String,
    onPassInputChanged: (String) -> Unit,
    onAuthClick: () -> Unit,
    errorMessage: String,
    authButtonEnabled: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .navigationBarsPadding()
            .imePadding()
    ) {

        AppBar(
            title = stringResource(R.string.auth_registration),
            onIconClick = onAuthClick,
            icon = Icons.Default.Close
        )

        Spacer(modifier = Modifier.height(16.dp))

        Crossfade(targetState = viewState, label = "") { state ->
            when (state) {
                ViewState.Loading -> LoadingBanner()
                else -> {
                    Column {
                        LoginPassInput(
                            email = email,
                            onEmailInputChanged = onEmailInputChanged,
                            password = password,
                            onPassInputChanged = onPassInputChanged,
                            onAuthClick = onAuthClick,
                            authButtonEnabled = authButtonEnabled
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AnimatedVisibility(visible = errorMessage.isNotBlank()) {
                            AuthErrorCard(message = errorMessage)
                        }
                    }
                }
            }

        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreenPreview() {
    Screen(
        onPassInputChanged = {},
        onEmailInputChanged = {},
        email = "Hello@.ru",
        password = "123456",
        viewState = ViewState.Content,
        onAuthClick = {},
        errorMessage = "",
        authButtonEnabled = true
    )
}