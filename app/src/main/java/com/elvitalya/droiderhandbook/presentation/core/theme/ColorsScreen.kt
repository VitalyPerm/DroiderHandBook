package com.elvitalya.droiderhandbook.presentation.core.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorsScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "primary", modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "onPrimary", modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "primaryContainer", modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "onPrimaryContainer", modifier = Modifier
                    .padding(16.dp),
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inversePrimary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "inversePrimary", modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "secondary", modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSecondary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "onSecondary", modifier = Modifier
                    .padding(16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "secondaryContainer", modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSecondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "onSecondaryContainer", modifier = Modifier
                    .padding(16.dp),
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "tertiary", modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onTertiary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "onTertiary", modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "tertiaryContainer", modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onTertiaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "tertiaryContainer", modifier = Modifier
                    .padding(16.dp),
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "background", modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "onBackground", modifier = Modifier
                    .padding(16.dp),
                color = Color.White
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "surface", modifier = Modifier
                    .padding(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSurface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "onSurface", modifier = Modifier
                    .padding(16.dp),
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "surfaceVariant", modifier = Modifier
                    .padding(16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSurfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "onSurfaceVariant", modifier = Modifier
                    .padding(16.dp),
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceTint),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "surfaceTint", modifier = Modifier
                    .padding(16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseSurface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "inverseSurface", modifier = Modifier
                    .padding(16.dp),
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseOnSurface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "inverseOnSurface", modifier = Modifier
                    .padding(16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "error", modifier = Modifier
                    .padding(16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onError),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "onError", modifier = Modifier
                    .padding(16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.errorContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "errorContainer", modifier = Modifier
                    .padding(16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onErrorContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "onErrorContainer", modifier = Modifier
                    .padding(16.dp),
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.outline),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "outline", modifier = Modifier
                    .padding(16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.outlineVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "outlineVariant", modifier = Modifier
                    .padding(16.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.scrim),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "outlineVariant", modifier = Modifier
                    .padding(16.dp),
                color = Color.White
            )
        }
    }
}