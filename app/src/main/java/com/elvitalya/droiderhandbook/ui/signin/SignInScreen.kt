package com.elvitalya.droiderhandbook.ui.signin

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.elvitalya.droiderhandbook.ui.main.MainActivity.Companion.TAG
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(onSuccess: () -> Unit) {

    var email by remember { mutableStateOf("") }

    var pass by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = loading.not()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = errorMessage, modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                )
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

                ElevatedButton(
                    onClick = {
                        loading = true
                        Firebase.auth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener {
                                loading = false
                                Log.d(TAG, "SignInScreen: isSuccessful = ${it.isSuccessful}")
                                if (it.isSuccessful) {
                                    onSuccess()
                                }
                            }
                            .addOnSuccessListener {
                                Log.d(TAG, "SignInScreen: success! ${it.user?.email}")
                            }
                            .addOnFailureListener { exception ->
                                if (exception is FirebaseAuthUserCollisionException) {
                                    Firebase.auth.signInWithEmailAndPassword(email, pass)
                                        .addOnCompleteListener {
                                            loading = false
                                            if (it.isSuccessful) onSuccess()
                                        }
                                        .addOnSuccessListener {
                                            Log.d(TAG, "SignInScreen: success! ${it.user?.email}")
                                        }
                                        .addOnFailureListener {
                                            errorMessage = exception.message ?: ""
                                        }
                                } else errorMessage = exception.message ?: ""
                            }
                    },
                    enabled = pass.isNotBlank() && email.isNotBlank()
                ) {
                    Text(text = "Login")
                }
            }
        }

        AnimatedVisibility(visible = loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

