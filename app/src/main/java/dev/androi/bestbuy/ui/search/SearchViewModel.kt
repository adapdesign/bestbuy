package dev.androi.bestbuy.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.androi.bestbuy.data.search.ProductItem
import dev.androi.bestbuy.data.search.SearchRepository
import dev.androi.bestbuy.utils.LanguageUtils
import dev.androi.bestbuy.utils.ApiResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class SearchViewModel(private val repo: SearchRepository) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState = MutableStateFlow<SearchUiStates>(SearchUiStates.Default)
    val uiState: StateFlow<SearchUiStates> = _uiState.asStateFlow()

    fun setQuery(q: String) {
        _query.value = q
    }

    private var searchJob: Job? = null

    fun submitSearch() {
        // Ignore white space queries
        val q = _query.value.trim()
        if (q.isEmpty()) return

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = SearchUiStates.Loading
            when (val response = repo.search(q, LanguageUtils.getLanguageCode())) {
                is ApiResult.Success -> {
                    if (response.data.products.isNullOrEmpty()) {
                        _uiState.value = SearchUiStates.NoResults
                    } else {
                        _uiState.value = SearchUiStates.Success(response.data.products)
                    }
                }

                is ApiResult.Failure -> {
                    if (response.throwable is CancellationException) {
                        // Ignore this case when job gets cancelled so it doesn't trigger error state
                    } else {
                        _uiState.value = SearchUiStates.Error(response.throwable)
                    }
                }
            }
        }
    }

    sealed class SearchUiStates {
        object Default : SearchUiStates()
        object Loading : SearchUiStates()
        object NoResults : SearchUiStates()
        data class Success(val data: List<ProductItem>) : SearchUiStates()
        data class Error(val throwable: Throwable) : SearchUiStates()
    }
}