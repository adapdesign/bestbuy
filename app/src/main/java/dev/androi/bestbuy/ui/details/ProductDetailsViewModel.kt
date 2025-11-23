package dev.androi.bestbuy.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.androi.bestbuy.data.details.ProductDetailsRepository
import dev.androi.bestbuy.data.details.ProductResponse
import dev.androi.bestbuy.utils.LanguageUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import dev.androi.bestbuy.utils.ApiResult
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel  @Inject constructor(val repo: ProductDetailsRepository, savedStateHandle: SavedStateHandle) : ViewModel() {
    private val id: String = checkNotNull(savedStateHandle.get<String>("id"))
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    // Track current gallery position
    private val _galleryIndex = MutableStateFlow(0)
    val galleryIndex: StateFlow<Int> = _galleryIndex.asStateFlow()
    fun setGalleryIndex(index: Int) {
        _galleryIndex.value = index
    }

    init {
        getDetails()
    }

    fun getDetails() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            when(val response = repo.getDetails(id, LanguageUtils.getLanguageCode())) {
                is ApiResult.Success -> _uiState.value = ProductUiState.Success(response.data)
                is ApiResult.Failure ->_uiState.value = ProductUiState.Error(response.throwable)
            }
        }
    }

    sealed class ProductUiState {
        object Loading : ProductUiState()
        data class Success(val data: ProductResponse) : ProductUiState()
        data class Error(val throwable: Throwable) : ProductUiState()
    }
}