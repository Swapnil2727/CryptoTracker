package com.app.presentation.ui.util

import java.text.NumberFormat
import java.util.Locale


/**
 * Format currency values with proper formatting
 */
internal fun formatCurrency(value: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    return when {
        value >= 1_000_000_000 -> {
            formatter.format(value / 1_000_000_000) + "B"
        }

        value >= 1_000_000 -> {
            formatter.format(value / 1_000_000) + "M"
        }

        value >= 1000 -> {
            formatter.format(value)
        }

        value >= 1 -> {
            String.format(Locale.US, "$%.2f", value)
        }

        value >= 0.01 -> {
            String.format(Locale.US, "$%.4f", value)
        }

        else -> {
            String.format(Locale.US, "$%.8f", value)
        }
    }
}

/**
 * Format large numbers with proper formatting
 */
internal fun formatNumber(value: Double): String {
    return when {
        value >= 1_000_000_000 -> String.format(Locale.US, "%.2fB", value / 1_000_000_000)
        value >= 1_000_000 -> String.format(Locale.US, "%.2fM", value / 1_000_000)
        value >= 1_000 -> String.format(Locale.US, "%,.0f", value)
        else -> String.format(Locale.US, "%.2f", value)
    }
}

/**
 * Format ISO date string to readable format
 */
internal fun formatDate(isoDate: String): String {
    return try {
        // Simple format - just show the date part
        isoDate.substringBefore('T')
    } catch (e: Exception) {
        isoDate
    }
}