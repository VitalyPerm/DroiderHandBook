package com.elvitalya.droiderhandbook.ui.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvitalya.droiderhandbook.R
import com.elvitalya.droiderhandbook.ui.theme.black
import com.elvitalya.droiderhandbook.ui.theme.accent
import com.elvitalya.droiderhandbook.ui.theme.white

@Composable
fun ErrorBanner() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = R.drawable.ic_error), contentDescription = null)
    }
}

@Composable
fun LoadingBanner() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(white),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(100.dp),
            color = accent
        )
    }
}

@Composable
fun AppBar(
    title: String,
    onCloseClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp),
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 24.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                color = black,
            )

            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .clip(CircleShape)
                    .background(accent)
                    .rippleClickable(onCloseClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(6.dp),
                    tint = black
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(accent)
        )
    }
}

@Composable
fun Modifier.rippleClickable(
    onClick: () -> Unit,
    enabled: Boolean = true
): Modifier {
    return clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(),
        role = Role.Button,
        onClick = onClick,
        enabled = enabled
    )
}