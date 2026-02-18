package com.example.minibankapp.uint

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minibankapp.viewmodel.DashboardUiState
import com.example.minibankapp.viewmodel.DashboardViewModel
import com.example.minibankapp.viewmodel.Transaction

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Account Balance: $1234.56",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {

            is DashboardUiState.Loading -> {
                CircularProgressIndicator()
            }

            is DashboardUiState.Success -> {
                val transactions =
                    (uiState as DashboardUiState.Success).transactions

                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    items(transactions) { txn ->
                        TransactionItem(txn)
                    }
                }
            }

            is DashboardUiState.Error -> {
                val message =
                    (uiState as DashboardUiState.Error).message

                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = transaction.merchant,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = transaction.date,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = transaction.amount,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
