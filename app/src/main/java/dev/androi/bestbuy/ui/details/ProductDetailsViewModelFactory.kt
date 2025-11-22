package dev.androi.bestbuy.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.androi.bestbuy.data.details.ProductDetailsRepository

class ProductDetailsViewModelFactory(
    private val id: String,
    private val repo: ProductDetailsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailsViewModel(id, repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}