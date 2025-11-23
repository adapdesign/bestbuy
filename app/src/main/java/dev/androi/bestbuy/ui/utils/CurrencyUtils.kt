package dev.androi.bestbuy.ui.utils

import java.text.NumberFormat
import java.util.Locale

fun Double.formatCurrency(locale: Locale = Locale.getDefault()): String {
    val formatter = NumberFormat.getCurrencyInstance(locale)
    return formatter.format(this)
}