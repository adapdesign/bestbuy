package dev.androi.bestbuy.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.androi.bestbuy.R

@Composable
fun SearchScreen(
    uiState: SearchViewModel.SearchUiStates,
    query: String,
    setQuery: (String) -> Unit,
    submitSearch: () -> Unit,
    onOpenProductDetails: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
    ) {
        SearchBar(query, setQuery, submitSearch)
        when (uiState) {
            is SearchViewModel.SearchUiStates.Default -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.initial_search_results)
                    )
                }
            }

            is SearchViewModel.SearchUiStates.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is SearchViewModel.SearchUiStates.NoResults -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.zero_search_results)
                    )
                }
            }

            is SearchViewModel.SearchUiStates.Success -> {
                val results = uiState.data
                LazyColumn {
                    items(results) { item ->
                        Card(
                            modifier = Modifier
                                .padding(vertical = 6.dp)
                                .clickable {
                                    onOpenProductDetails.invoke(item.sku)
                                }) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                ThumbnailBox(
                                    thumbnailImage = item.thumbnailImage,
                                    itemName = item.name
                                )
                                DescriptionBox(item)
                            }
                        }
                    }
                }
            }

            is SearchViewModel.SearchUiStates.Error -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.search_fetch_error))
                }
            }
        }
    }
}