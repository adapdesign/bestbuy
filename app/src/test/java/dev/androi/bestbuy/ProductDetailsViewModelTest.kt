package dev.androi.bestbuy

import dev.androi.bestbuy.data.details.ProductDetailsRepository
import dev.androi.bestbuy.data.details.ProductResponse
import dev.androi.bestbuy.ui.details.ProductDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
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
        repo = mockk()
        vm = ProductDetailsViewModel(repo, "id")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test success case`() = runTest {
        coEvery { repo.getDetails(any(), any()) } returns ProductResponse("", "", listOf(), "", 0.0, 0.0)

        vm.getDetails("test")
        advanceUntilIdle()
        val state = vm.uiState.value
        assertTrue(state is ProductDetailsViewModel.ProductUiState.Success)
    }

    @Test
    fun `test error case`() = runTest {
        val ex = RuntimeException("fail")
        coEvery { repo.getDetails(any(), any()) } throws ex

        vm.getDetails("test")
        advanceUntilIdle()
        val state = vm.uiState.value
        assertTrue(state is ProductDetailsViewModel.ProductUiState.Error)
        val err = state as ProductDetailsViewModel.ProductUiState.Error
        assertSame(ex, err.throwable)
    }
}