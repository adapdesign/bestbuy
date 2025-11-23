package dev.androi.bestbuy

import dev.androi.bestbuy.data.search.ProductItem
import dev.androi.bestbuy.data.search.SearchRepository
import dev.androi.bestbuy.data.search.SearchResponse
import dev.androi.bestbuy.ui.search.SearchViewModel
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

class SearchViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    lateinit var repo: SearchRepository
    lateinit var vm: SearchViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        repo = mockk()
        vm = SearchViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test submitting empty results`() = runTest {
        coEvery { repo.search("q") } returns SearchResponse(products = emptyList())

        vm.setQuery("   ")
        vm.submitSearch()
        advanceUntilIdle()
        val state = vm.uiState.value
        assertTrue(state is SearchViewModel.SearchUiStates.Default)
    }

    @Test
    fun `test submitting success case`() = runTest {
        val items = listOf(
            ProductItem(
                sku = "123",
                name = "Test Item",
                thumbnailImage = "test.jpg",
                regularPrice = 123.22,
                salePrice = 103.11
            )
        )
        coEvery { repo.search("abc") } returns SearchResponse(products = items)

        vm.setQuery("abc")
        vm.submitSearch()
        advanceUntilIdle()

        val state = vm.uiState.value
        assertTrue(state is SearchViewModel.SearchUiStates.Success)
    }

    @Test
    fun `test submitting for empty or zero results`() = runTest {
        coEvery { repo.search("q") } returns SearchResponse(products = emptyList())

        vm.setQuery("q")
        vm.submitSearch()
        advanceUntilIdle()

        assertTrue(vm.uiState.value is SearchViewModel.SearchUiStates.NoResults)
    }

    @Test
    fun `test submitting for error state`() = runTest {
        val ex = RuntimeException("fail")
        coEvery { repo.search("q") } throws ex

        vm.setQuery("q")
        vm.submitSearch()
        advanceUntilIdle()

        val state = vm.uiState.value
        assertTrue(state is SearchViewModel.SearchUiStates.Error)
        val err = state as SearchViewModel.SearchUiStates.Error
        assertSame(ex, err.throwable)
    }
}