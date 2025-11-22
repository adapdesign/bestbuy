package dev.androi.bestbuy.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.androi.bestbuy.data.search.SearchRepository

class SearchViewModelFactory(
    private val repo: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}