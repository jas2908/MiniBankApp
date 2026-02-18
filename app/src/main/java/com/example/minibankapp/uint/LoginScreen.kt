package com.example.minibankapp.uint

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.minibankapp.viewmodel.LoginUiState
import com.example.minibankapp.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val uiState by loginViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "MiniBank Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { loginViewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState != LoginUiState.Loading
        ) {
            Text(
                when (uiState) {
                    LoginUiState.Loading -> "Logging in..."
                    else -> "Login"
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (uiState) {
            is LoginUiState.Error -> {
                Text(
                    text = (uiState as LoginUiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            is LoginUiState.Success -> {
                // Navigate to dashboard
                LaunchedEffect(Unit) {
                    navController.navigate("dashboard")
                    loginViewModel.resetState()
                }
            }

            else -> { /* Idle or Loading, do nothing extra */ }
        }
    }
}
