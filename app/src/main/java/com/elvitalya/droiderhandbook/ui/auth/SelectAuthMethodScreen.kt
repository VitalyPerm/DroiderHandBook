package com.elvitalya.droiderhandbook.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elvitalya.droiderhandbook.R
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun SelectAuthMethodScreen(
    onAuthMethodSelected: (AuthMethod) -> Unit
) {
    ProvideWindowInsets {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.tertiary),
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
                        .clickable { onAuthMethodSelected(AuthMethod.LOGIN) },
                    contentAlignment = Alignment.Center
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
                    contentAlignment = Alignment.Center
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
}