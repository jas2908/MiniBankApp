package com.example.minibankapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String) {

        if (email.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Please enter email and password")
            return
        }

        // Simulate login success
        _uiState.value = LoginUiState.Loading

        // Immediately succeed (we keep it simple)
        _uiState.value = LoginUiState.Success
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}
