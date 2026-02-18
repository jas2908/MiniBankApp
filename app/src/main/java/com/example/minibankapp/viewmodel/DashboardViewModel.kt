package com.example.minibankapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Transaction(
    val merchant: String,
    val amount: String,
    val date: String
)

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(val transactions: List<Transaction>) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}

class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            delay(1500) // simulate network delay

            try {
                val transactions = listOf(
                    Transaction("Coffee Shop", "-NOK 50", "17 Feb 2026"),
                    Transaction("Grocery Store", "-NOK 230", "16 Feb 2026"),
                    Transaction("Salary", "+NOK 35000", "15 Feb 2026"),
                    Transaction("Gym", "-NOK 45", "14 Feb 2026")
                )

                _uiState.value = DashboardUiState.Success(transactions)

            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error("Failed to load transactions")
            }
        }
    }
}
