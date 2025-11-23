package dev.androi.bestbuy.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.androi.bestbuy.data.details.ProductDetailsRepository
import dev.androi.bestbuy.data.details.ProductResponse
import dev.androi.bestbuy.ui.utils.LanguageUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(val repo: ProductDetailsRepository, id: String) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    // Track current gallery position
    private val _galleryIndex = MutableStateFlow(0)
    val galleryIndex: StateFlow<Int> = _galleryIndex.asStateFlow()
    fun setGalleryIndex(index: Int) {
        _galleryIndex.value = index
    }

    init {
        getDetails(id)
    }

    fun getDetails(id: String) {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            try {
                val response = repo.getDetails(id, LanguageUtils.getLanguageCode())
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