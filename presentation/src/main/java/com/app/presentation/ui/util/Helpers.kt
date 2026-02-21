package com.app.presentation.ui.util

import java.text.NumberFormat
import java.util.Locale

/**
 * Format currency values with proper formatting
 */
internal fun formatCurrency(value: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    return when {
        value >= BILLION -> {
            formatter.format(value / BILLION) + "B"
        }

        value >= MILLION -> {
            formatter.format(value / MILLION) + "M"
        }

        value >= THOUSAND -> {
            formatter.format(value)
        }

        value >= 1 -> {
            String.format(Locale.US, "$%.2f", value)
        }

        value >= SMALL_VALUE_THRESHOLD -> {
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
        value >= BILLION -> String.format(Locale.US, "%.2fB", value / BILLION)
        value >= MILLION -> String.format(Locale.US, "%.2fM", value / MILLION)
        value >= THOUSAND -> String.format(Locale.US, "%,.0f", value)
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

private const val BILLION = 1_000_000_000.0
private const val MILLION = 1_000_000.0
private const val THOUSAND = 1_000.0
private const val SMALL_VALUE_THRESHOLD = 0.01
