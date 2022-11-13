package com.elvitalya.droiderhandbook.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    onNavigateToMain: () -> Unit,
) {

    var email by remember {
        mutableStateOf("")
    }

    var pass by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = {
                    Text(text = "Enter email")
                })

            Spacer(modifier = Modifier.height(24.dp))

            TextField(value = pass,
                onValueChange = { pass = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = {
                    Text(text = "Enter password")
                })

            Spacer(modifier = Modifier.height(24.dp))

            ElevatedButton(onClick = {
                Firebase.auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            onNavigateToMain()
                        }
                    }
            }) {
                Text(text = "Login")
            }
        }
    }
}