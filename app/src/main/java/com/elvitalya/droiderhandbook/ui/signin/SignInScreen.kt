package com.elvitalya.droiderhandbook.ui.signin

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elvitalya.droiderhandbook.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    onSuccess: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {

    val screenState by viewModel.screenState.collectAsState()

    Crossfade(
        targetState = screenState.authMethod,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
    ) { loginSelected ->
        when (loginSelected) {
            SignInScreenAuthMethod.UNSELECTED -> {
                LoginOrRegisterOption { authMethod ->
                    viewModel.onAuthMethodSelected(authMethod)
                }
            }
            else -> {
                LoginOrRegisterInput(
                    screenState = screenState,
                    onEmailInputChanged = viewModel::onEmailInputChanged,
                    onPassInputChanged = viewModel::onPassInputChanged,
                    onClickArrow = viewModel::onClickArrow
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginOrRegisterInput(
    screenState: SignInScreenState,
    onEmailInputChanged: (String) -> Unit,
    onPassInputChanged: (String) -> Unit,
    onClickArrow: () -> Unit
) {
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
                text = if (screenState.authMethod == SignInScreenAuthMethod.LOGIN) "Вход" else "Регистрация",
                modifier = Modifier
                    .padding(top = 24.dp)
                    .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(16.dp))
                    .padding(24.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(screenState.errorMessage.isNotBlank()) {
                Text(
                    text = screenState.errorMessage,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Red
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .background(
                    MaterialTheme.colorScheme.onTertiary,
                    RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                ),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = screenState.email,
                    onValueChange = { onEmailInputChanged(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    label = {
                        Text(
                            text = "Почта",
                            color = Color.Black
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = screenState.pass,
                    onValueChange = { onPassInputChanged(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    label = {
                        Text(
                            text = "Пароль",
                            color = Color.Black
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
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

}

@Composable
private fun LoginOrRegisterOption(
    onAuthMethodSelected: (SignInScreenAuthMethod) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(16.dp))
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .clickable { onAuthMethodSelected(SignInScreenAuthMethod.LOGIN) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Вход",
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
                    .clickable { onAuthMethodSelected(SignInScreenAuthMethod.REGISTRATION) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Регистрация",
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(horizontal = 24.dp),
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }

}

