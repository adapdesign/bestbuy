package dev.androi.bestbuy.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.androi.bestbuy.data.details.ProductDetailsRepository
import dev.androi.bestbuy.data.details.ProductResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(id: String, val repo: ProductDetailsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    fun getDetails(id: String) {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            try {
                val response = repo.getDetails(id)
                _uiState.value = ProductUiState.Success(response)
            } catch (t: Throwable) {
                _uiState.value = ProductUiState.Error(t)
            }
        }
    }

    sealed class ProductUiState {
        object Loading : ProductUiState()
        data class Success(val data: ProductResponse) : ProductUiState()
        data class Error(val throwable: Throwable) : ProductUiState()
    }
}