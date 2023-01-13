package com.elvitalya.droiderhandbook.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.ui.core.AppBar
import com.elvitalya.droiderhandbook.ui.core.LoadingBanner
import com.elvitalya.droiderhandbook.ui.core.rippleClickable
import com.elvitalya.droiderhandbook.ui.theme.*
import com.elvitalya.droiderhandbook.utils.ViewState
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding


@Composable
fun AuthScreen(
    authMethod: AuthMethod,
    email: String,
    password: String,
    viewState: ViewState,
    errorMessage: String,
    onEmailInputChanged: (String) -> Unit,
    onPassInputChanged: (String) -> Unit,
    onAuthClick: () -> Unit,
    authButtonEnabled: Boolean,
    onCloseClick: () -> Unit
) {

    ProvideWindowInsets {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(white)
                .navigationBarsWithImePadding()
        ) {
            val title = stringResource(
                id = if (authMethod == AuthMethod.LOGIN)
                    R.string.sign_in else R.string.registration
            )

            AppBar(
                title = title,
                onCloseClick = onCloseClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            Crossfade(targetState = viewState) { viewState ->
                when (viewState) {
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
                                ErrorCard(message = errorMessage)
                            }
                        }
                    }
                }

            }

        }
    }

}

@Composable
fun LoginPassInput(
    email: String,
    onEmailInputChanged: (String) -> Unit,
    password: String,
    onPassInputChanged: (String) -> Unit,
    onAuthClick: () -> Unit,
    authButtonEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                clip = true
            )
            .background(
                white,
                RoundedCornerShape(16.dp)
            ),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = CenterHorizontally
        ) {

            BasicTextField(
                value = email,
                onValueChange = onEmailInputChanged,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                textStyle = TextStyle(
                    color = black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                ),
                cursorBrush = SolidColor(accent),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(inActive, RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        if (email.isEmpty()) {
                            Text(
                                text = stringResource(R.string.email),
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Start,
                                color = hint,
                            )
                        }

                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            var passwordVisible by remember { mutableStateOf(false) }

            val toggleIcon = if (passwordVisible) R.drawable.ic_password_eye_visible
            else R.drawable.ic_password_eye_invisible

            BasicTextField(
                value = password,
                onValueChange = onPassInputChanged,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                textStyle = TextStyle(
                    color = black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                ),
                cursorBrush = SolidColor(accent),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(inActive, RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        if (password.isEmpty()) {
                            Text(
                                text = stringResource(R.string.pass),
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Start,
                                color = hint,
                            )
                        }

                        innerTextField()

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .rippleClickable({
                                    passwordVisible = passwordVisible.not()
                                })
                                .padding(4.dp)
                                .align(CenterEnd),
                            contentAlignment = Center
                        ) {
                            Image(
                                painterResource(id = toggleIcon),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(black)
                            )
                        }
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation()
            )

        }

        val btnBgColor by animateColorAsState(
            targetValue = if (authButtonEnabled) accent else inActive
        )

        Box(
            modifier = Modifier
                .padding(6.dp)
                .clip(CircleShape)
                .background(btnBgColor)
                .align(CenterVertically)
                .rippleClickable(onAuthClick, enabled = authButtonEnabled),
            contentAlignment = Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp),
                tint = black
            )
        }
    }
}


@Composable
private fun ErrorCard(message: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(4.dp, shape = RoundedCornerShape(16.dp))
            .background(white),
        contentAlignment = Center
    ) {
        Text(
            text = message,
            modifier = Modifier
                .padding(16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = error
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen(
        authMethod = AuthMethod.LOGIN,
        email = "hello@android.ru",
        password = "123",
        viewState = ViewState.Content,
        errorMessage = "Some Error occured",
        onEmailInputChanged = {},
        onPassInputChanged = {},
        onAuthClick = { },
        authButtonEnabled = true,
        onCloseClick = {}
    )
}

