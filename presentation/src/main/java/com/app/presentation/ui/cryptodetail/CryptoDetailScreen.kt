package com.app.presentation.ui.cryptodetail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.presentation.R
import com.app.presentation.model.CryptoDetailState
import com.app.presentation.viewmodel.cryptodetail.CryptoDetailViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Detail screen showing comprehensive information about a single cryptocurrency.
 *
 * Features:
 * - Crypto image, name, symbol
 * - Current price and 24h change
 * - Market stats (market cap, volume, supply)
 * - All-time high/low stats
 * - Back navigation
 * - Loading and error states
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoDetailScreen(
    viewModel: CryptoDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crypto Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        when (val currentState = state) {
            is CryptoDetailState.Loading -> {
                LoadingContent(modifier = Modifier.padding(paddingValues))
            }

            is CryptoDetailState.Success -> {
                CryptoDetailSuccessContent(
                    crypto = currentState.crypto,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is CryptoDetailState.Error -> {
                ErrorContent(
                    message = currentState.message,
                    onRetry = viewModel::onRetry,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}


