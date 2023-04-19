package com.elvitalya.presentation.ui.authentication

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.presentation.R
import com.elvitalya.presentation.core.rippleClickable
import com.elvitalya.presentation.theme.accent
import com.elvitalya.presentation.theme.black
import com.elvitalya.presentation.theme.hint
import com.elvitalya.presentation.theme.inActive
import com.elvitalya.presentation.theme.white



@Composable
fun AuthErrorCard(message: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(4.dp, shape = RoundedCornerShape(16.dp))
            .background(white),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            modifier = Modifier
                .padding(16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = com.elvitalya.presentation.theme.error
        )
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
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
                        if (email.isBlank()) {
                            Text(
                                text = stringResource(R.string.auth_email),
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
                        if (password.isBlank()) {
                            Text(
                                text = stringResource(R.string.auth_pass),
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
                                .rippleClickable(onClick = {
                                    passwordVisible = passwordVisible.not()
                                })
                                .padding(4.dp)
                                .align(Alignment.CenterEnd),
                            contentAlignment = Alignment.Center
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
            targetValue = if (authButtonEnabled) accent else inActive, label = ""
        )

        Box(
            modifier = Modifier
                .padding(6.dp)
                .clip(CircleShape)
                .background(btnBgColor)
                .align(Alignment.CenterVertically)
                .rippleClickable(onClick = onAuthClick, enabled = authButtonEnabled),
            contentAlignment = Alignment.Center
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