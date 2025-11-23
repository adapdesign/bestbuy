package dev.androi.bestbuy.utils

import java.util.Locale

object LanguageUtils {

    // Map device locale to API language code
    fun getLanguageCode(): String {
        val locale = Locale.getDefault()
        return if (locale.language == "fr") {
            "fr"
        } else {
            "en"
        }
    }
}