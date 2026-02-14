package com.app.presentation.ui.cryptolist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.app.domain.model.CryptoCurrency
import com.app.presentation.model.CryptoListState
import com.app.presentation.theme.CryptoTrackerTheme
import com.app.presentation.ui.util.previewCryptoList
import com.app.presentation.viewmodel.cryptolist.CryptoListViewModel
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale

/**
 * Main screen showing list of top cryptocurrencies.
 *
 * Features:
 * - List of top 50 cryptos by market cap
 * - Pull to refresh (Material 3 style)
 * - Loading state
 * - Error state with retry
 * - Click to navigate to detail screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoListScreen(
    viewModel: CryptoListViewModel = koinViewModel(),
    onCryptoClick: (String) -> Unit,
) {
    // Collect state from ViewModel
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CryptoTracker",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        // Handle different states
        when (val currentState = state) {
            is CryptoListState.Loading -> {
                LoadingContent(modifier = Modifier.padding(paddingValues))
            }

            is CryptoListState.Success -> {
                CryptoListContent(
                    cryptos = currentState.cryptos,
                    isRefreshing = currentState.isRefreshing,
                    onRefresh = viewModel::onRefresh,
                    onCryptoClick = onCryptoClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is CryptoListState.Error -> {
                ErrorContent(
                    message = currentState.message,
                    onRetry = viewModel::onRetry,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

/**
 * Loading state - shows a centered progress indicator with Material 3 styling
 */
@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
            Text(
                text = "Loading cryptocurrencies...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Success state - shows list of cryptocurrencies with pull-to-refresh (Material 3)
 */
@Composable
private fun CryptoListContent(
    cryptos: List<CryptoCurrency>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onCryptoClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Material 3 Pull to Refresh
    val pullToRefreshState = rememberPullToRefreshState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (pullToRefreshState.isAnimating) {
            // Trigger refresh when user pulls down
            onRefresh()
        }

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = cryptos,
                    key = { it.id }
                ) { crypto ->
                    CryptoListItem(
                        crypto = crypto,
                        onClick = { onCryptoClick(crypto.id) }
                    )
                }
            }
        }
    }
}

/**
 * Individual crypto item in the list - Material 3 Card design
 */
@Composable
private fun CryptoListItem(
    crypto: CryptoCurrency,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Crypto image with Material 3 styling
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp
            ) {
                AsyncImage(
                    model = crypto.image,
                    contentDescription = "${crypto.name} logo",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Name and symbol
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = crypto.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = crypto.symbol.uppercase(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }

            // Price and change
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${formatPrice(crypto.currentPrice)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Material 3 color scheme for positive/negative changes
                val changeColor = if (crypto.priceChangePercentage24h >= 0) {
                    MaterialTheme.colorScheme.tertiary // Green tone in M3
                } else {
                    MaterialTheme.colorScheme.error // Red tone in M3
                }

                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = changeColor.copy(alpha = 0.12f),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = "${if (crypto.priceChangePercentage24h >= 0) "+" else ""}${
                            String.format(Locale.US, "%.2f", crypto.priceChangePercentage24h)
                        }%",
                        style = MaterialTheme.typography.labelMedium,
                        color = changeColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

/**
 * Error state - shows error message with retry button (Material 3 styling)
 */
@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            // Error icon using Material 3 surface
            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.errorContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "!",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            Text(
                text = "Oops! Something went wrong",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Material 3 filled button
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "Retry",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

/**
 * Helper function to format price based on value
 */
private fun formatPrice(price: Double): String {
    return when {
        price >= 1000 -> String.format(Locale.US, "%,.0f", price)
        price >= 1 -> String.format(Locale.US, "%.2f", price)
        price >= 0.01 -> String.format(Locale.US, "%.4f", price)
        else -> String.format(Locale.US, "%.6f", price)
    }
}

@Composable
@PreviewScreenSizes
private fun CryptoListContentPreview() {
    CryptoTrackerTheme {
        CryptoListContent(
            cryptos = previewCryptoList,
            isRefreshing = false,
            onRefresh = {},
            onCryptoClick = {}
        )
    }
}