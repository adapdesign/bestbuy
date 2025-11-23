package dev.androi.bestbuy.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.androi.bestbuy.data.details.ProductDetailsRepository

class ProductDetailsViewModelFactory(
    private val repo: ProductDetailsRepository,
    private val id: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailsViewModel(repo, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}