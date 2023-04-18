package com.elvitalya.presentation.ui.select_auth_method

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.presentation.R
import com.elvitalya.presentation.theme.accent
import com.elvitalya.presentation.theme.inActive
import com.elvitalya.presentation.theme.white

@Composable
fun SelectAuthMethodScreen(
    onAuthClick: () -> Unit,
    onRegClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(white),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = stringResource(R.string.auth_title),
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onRegClick)
                .background(accent)
                .padding(vertical = 8.dp)
                .align(Alignment.TopCenter),
            color = MaterialTheme.colorScheme.onTertiary,
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold
        )


        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .background(inActive, RoundedCornerShape(16.dp))
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.auth_sign_in),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(onClick = onAuthClick)
                    .background(accent)
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onTertiary,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = stringResource(R.string.auth_registration),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(onClick = onRegClick)
                    .background(accent)
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onTertiary,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SelectAuthMethodPreview() {
    SelectAuthMethodScreen(
        onAuthClick = {},
        onRegClick = {}
    )
}