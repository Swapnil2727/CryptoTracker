package com.app.presentation.ui.cryptodetail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.domain.model.CryptoCurrency
import com.app.presentation.ui.util.formatCurrency
import com.app.presentation.ui.util.formatDate
import com.app.presentation.ui.util.formatNumber
import com.app.presentation.ui.util.previewBitcoin
import com.app.presentation.ui.util.previewEthereumNegative
import java.util.Locale

/**
 * Success state - shows detailed crypto information
 */
@Composable
internal fun CryptoDetailSuccessContent(
    crypto: CryptoCurrency,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(64.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Section
        CryptoHeader(crypto = crypto)

        HorizontalDivider()

        // Market Stats Section
        Text(
            text = "Market Statistics",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        MarketStatsSection(crypto = crypto)

        HorizontalDivider()

        // Supply Information
        Text(
            text = "Supply Information",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        SupplySection(crypto = crypto)

        HorizontalDivider()

        // All-Time Stats
        Text(
            text = "All-Time Statistics",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        AllTimeStatsSection(crypto = crypto)
    }
}

@Preview(name = "Detail Content - Light", showBackground = true)
@Preview(
    name = "Detail Content - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
internal fun CryptoDetailSuccessContentPreview() {
    MaterialTheme {
        CryptoDetailSuccessContent(crypto = previewBitcoin)
    }
}

/**
 * Header section with crypto image, name, price, and 24h change
 */
@Composable
private fun CryptoHeader(
    crypto: CryptoCurrency,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Crypto Image
        Surface(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant,
            tonalElevation = 4.dp
        ) {
            AsyncImage(
                model = crypto.image,
                contentDescription = "${crypto.name} logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .padding(16.dp)
            )
        }

        // Name and Symbol
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = crypto.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = crypto.symbol.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }

        // Current Price
        Text(
            text = formatCurrency(crypto.currentPrice),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        // 24h Change
        val changeColor = if (crypto.priceChangePercentage24h >= 0) {
            MaterialTheme.colorScheme.tertiary
        } else {
            MaterialTheme.colorScheme.error
        }

        Surface(
            shape = MaterialTheme.shapes.medium,
            color = changeColor.copy(alpha = 0.12f)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${if (crypto.priceChangePercentage24h >= 0) "+" else ""}${
                        String.format(Locale.US, "%.2f", crypto.priceChangePercentage24h)
                    }%",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = changeColor
                )
                Text(
                    text = "(${formatCurrency(crypto.priceChange24h)})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = changeColor
                )
            }
        }
    }
}

@Preview(name = "Crypto Header - Positive", showBackground = true)
@Composable
private fun CryptoHeaderPositivePreview() {
    MaterialTheme {
        CryptoHeader(crypto = previewBitcoin)
    }
}

@Preview(name = "Crypto Header - Negative", showBackground = true)
@Composable
private fun CryptoHeaderNegativePreview() {
    MaterialTheme {
        CryptoHeader(crypto = previewEthereumNegative)
    }
}

/**
 * Market statistics section
 */
@Composable
private fun MarketStatsSection(
    crypto: CryptoCurrency,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatRow(
            label = "Market Cap Rank",
            value = "#${crypto.marketCapRank}"
        )

        StatRow(
            label = "Market Cap",
            value = formatCurrency(crypto.marketCap.toDouble())
        )

        StatRow(
            label = "Market Cap Change (24h)",
            value = "${if (crypto.marketCapChangePercentage24h >= 0) "+" else ""}${
                String.format(Locale.US, "%.2f", crypto.marketCapChangePercentage24h)
            }%",
            valueColor = if (crypto.marketCapChangePercentage24h >= 0) {
                MaterialTheme.colorScheme.tertiary
            } else {
                MaterialTheme.colorScheme.error
            }
        )

        StatRow(
            label = "Total Volume (24h)",
            value = formatCurrency(crypto.totalVolume.toDouble())
        )

        StatRow(
            label = "24h High",
            value = formatCurrency(crypto.high24h)
        )

        StatRow(
            label = "24h Low",
            value = formatCurrency(crypto.low24h)
        )
    }
}

@Preview(name = "Market Stats Section", showBackground = true)
@Composable
private fun MarketStatsSectionPreview() {
    MaterialTheme {
        Surface {
            MarketStatsSection(
                crypto = previewBitcoin,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * Supply information section
 */
@Composable
private fun SupplySection(
    crypto: CryptoCurrency,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatRow(
            label = "Circulating Supply",
            value = formatNumber(crypto.circulatingSupply)
        )

        crypto.totalSupply?.let {
            StatRow(
                label = "Total Supply",
                value = formatNumber(it)
            )
        }

        crypto.maxSupply?.let {
            StatRow(
                label = "Max Supply",
                value = formatNumber(it)
            )
        } ?: run {
            StatRow(
                label = "Max Supply",
                value = "∞ (Unlimited)",
                valueColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}

@Preview(name = "Supply Section", showBackground = true)
@Composable
private fun SupplySectionPreview() {
    MaterialTheme {
        Surface {
            SupplySection(
                crypto = previewBitcoin,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * All-time statistics section
 */
@Composable
private fun AllTimeStatsSection(
    crypto: CryptoCurrency,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // All-Time High
        Text(
            text = "All-Time High",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        StatRow(
            label = "Price",
            value = formatCurrency(crypto.ath)
        )

        StatRow(
            label = "Change from ATH",
            value = "${String.format(Locale.US, "%.2f", crypto.athChangePercentage)}%",
            valueColor = MaterialTheme.colorScheme.error
        )

        StatRow(
            label = "Date",
            value = formatDate(crypto.athDate)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // All-Time Low
        Text(
            text = "All-Time Low",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        StatRow(
            label = "Price",
            value = formatCurrency(crypto.atl)
        )

        StatRow(
            label = "Change from ATL",
            value = "+${String.format(Locale.US, "%.2f", crypto.atlChangePercentage)}%",
            valueColor = MaterialTheme.colorScheme.tertiary
        )

        StatRow(
            label = "Date",
            value = formatDate(crypto.atlDate)
        )
    }
}

@Preview(name = "All-Time Stats Section", showBackground = true)
@Composable
private fun AllTimeStatsSectionPreview() {
    MaterialTheme {
        Surface {
            AllTimeStatsSection(
                crypto = previewBitcoin,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * Reusable stat row component
 */
@Composable
private fun StatRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = MaterialTheme.colorScheme.onBackground
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = valueColor
        )
    }
}

@Preview(name = "Stat Row", showBackground = true)
@Composable
private fun StatRowPreview() {
    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatRow(label = "Market Cap", value = "$1,234,567,890")
                StatRow(
                    label = "24h Change",
                    value = "+9.44%",
                    valueColor = MaterialTheme.colorScheme.tertiary
                )
                StatRow(
                    label = "Change from ATH",
                    value = "-44.41%",
                    valueColor = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
