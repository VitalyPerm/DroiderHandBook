package com.elvitalya.droiderhandbook.ui.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.utils.ViewState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(
    authMethod: AuthMethod,
    email: String,
    password: String,
    viewState: ViewState,
    errorMessage: String?,
    onEmailInputChanged: (String) -> Unit,
    onPassInputChanged: (String) -> Unit,
    onClickLogin: () -> Unit
) {

    ProvideWindowInsets {
        Crossfade(
            targetState = authMethod,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiary)
                .navigationBarsWithImePadding()
        ) { authMethod ->
            AnimatedContent(targetState = viewState) { viewState ->
                when (viewState) {
                    ViewState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(), contentAlignment = Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(100.dp),
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        }
                    }

                    else -> {
                        InputScreen(
                            authMethod = authMethod,
                            email = email,
                            password = password,
                            errorMessage = errorMessage,
                            onEmailInputChanged = onEmailInputChanged,
                            onPassInputChanged = onPassInputChanged,
                            onClickArrow = onClickLogin
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun InputScreen(
    authMethod: AuthMethod,
    email: String,
    password: String,
    onEmailInputChanged: (String) -> Unit,
    onPassInputChanged: (String) -> Unit,
    onClickArrow: () -> Unit,
    errorMessage: String?
) {

    val title = stringResource(
        id = if (authMethod == AuthMethod.LOGIN)
            R.string.sign_in else R.string.registration
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.CenterStart
    ) {

        Column(
            modifier = Modifier
                .align(TopCenter),
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(16.dp))
                    .padding(24.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            errorMessage?.let { error ->
                ErrorCard(message = error)
            }
        }

        LoginPassInput(
            email = email,
            onEmailInputChanged = onEmailInputChanged,
            password = password,
            onPassInputChanged = onPassInputChanged,
            onClickArrow = onClickArrow
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPassInput(
    email: String,
    onEmailInputChanged: (String) -> Unit,
    password: String,
    onPassInputChanged: (String) -> Unit,
    onClickArrow: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .background(
                MaterialTheme.colorScheme.onTertiary,
                RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
            ),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = CenterHorizontally
        ) {
            TextField(
                value = email,
                onValueChange = { onEmailInputChanged(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = {
                    Text(
                        text = stringResource(id = R.string.email),
                        color = Color.Black
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = password,
                onValueChange = { onPassInputChanged(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = {
                    Text(
                        text = stringResource(id = R.string.pass),
                        color = Color.Black
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp)
            )

        }
        IconButton(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.tertiary, CircleShape)
                .align(CenterVertically),
            onClick = onClickArrow
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null
            )
        }
    }
}


@Composable
private fun ErrorCard(message: String) {
    AnimatedVisibility(message.isNotBlank()) {
        Text(
            text = message,
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(16.dp))
                .padding(24.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Red
        )
    }
}

@Composable
private fun LoginOrRegisterChooserScreen(
    onAuthMethodSelected: (AuthMethod) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(16.dp))
                .padding(vertical = 24.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .clickable { onAuthMethodSelected(AuthMethod.LOGIN) },
                contentAlignment = Center
            ) {
                Text(
                    text = stringResource(id = R.string.sign_in),
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(horizontal = 24.dp),
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .clickable { onAuthMethodSelected(AuthMethod.REGISTRATION) },
                contentAlignment = Center
            ) {
                Text(
                    text = stringResource(id = R.string.registration),
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(horizontal = 24.dp),
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }

}

