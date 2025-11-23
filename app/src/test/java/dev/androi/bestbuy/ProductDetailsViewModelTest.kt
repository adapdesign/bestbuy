package dev.androi.bestbuy

import androidx.lifecycle.SavedStateHandle
import dev.androi.bestbuy.data.details.ProductDetailsRepository
import dev.androi.bestbuy.data.details.ProductResponse
import dev.androi.bestbuy.ui.details.ProductDetailsViewModel
import dev.androi.bestbuy.utils.ApiResult
import dev.androi.bestbuy.utils.failure
import dev.androi.bestbuy.utils.success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProductDetailsViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    lateinit var repo: ProductDetailsRepository
    lateinit var vm: ProductDetailsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test success case`() = runTest {
        repo = object: ProductDetailsRepository {
            override suspend fun getDetails(
                id: String,
                lang: String
            ): ApiResult<ProductResponse> {
                return success(ProductResponse(
                    "",
                    "",
                    listOf(),
                    "",
                    0.0,
                    0.0
                ))
            }
        }
        val handle = SavedStateHandle(mapOf("id" to "id"))
        vm = ProductDetailsViewModel(repo, handle)
        vm.getDetails()
        advanceUntilIdle()
        val state = vm.uiState.value
        assertTrue(state is ProductDetailsViewModel.ProductUiState.Success)
    }

    @Test
    fun `test error case`() = runTest {
        val ex = RuntimeException("fail")
        repo = object: ProductDetailsRepository {
            override suspend fun getDetails(
                id: String,
                lang: String
            ): ApiResult<ProductResponse> {
                return failure(ex)
            }
        }
        val handle = SavedStateHandle(mapOf("id" to "id"))
        vm = ProductDetailsViewModel(repo, handle)

        vm.getDetails()
        advanceUntilIdle()
        val state = vm.uiState.value
        assertTrue(state is ProductDetailsViewModel.ProductUiState.Error)
        val err = state as ProductDetailsViewModel.ProductUiState.Error
        assertSame(ex, err.throwable)
    }
}