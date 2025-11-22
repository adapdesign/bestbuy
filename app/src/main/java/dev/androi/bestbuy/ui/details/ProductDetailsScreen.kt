package dev.androi.bestbuy.ui.details

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.androi.bestbuy.R
import dev.androi.bestbuy.ui.details.ProductDetailsViewModel.ProductUiState
import dev.androi.bestbuy.ui.search.formatCurrency

@Composable
fun BackIcon() {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
        contentDescription = "Back"
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(vm: ProductDetailsViewModel, oBack: () -> Unit) {
    val uiState by vm.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val ctx = LocalContext.current

    Column(modifier = Modifier.systemBarsPadding().fillMaxSize().verticalScroll(scrollState)) {
        when (uiState) {
            is ProductUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ProductUiState.Success -> {
                val results = (uiState as ProductUiState.Success).data
                TopAppBar(title = { Text("") }, navigationIcon = {
                    IconButton(onClick = oBack) {
                        BackIcon()
                    }
                })
                Text(results.name.toString(), fontSize = 20.sp)
                if (results.additionalMedia.isNotEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(results.additionalMedia.first().url)
                            .crossfade(true)
                            .build(),
                        contentDescription = results.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(320.dp)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp)))
                }
                if (results.regularPrice != null) {
                    Text(results.regularPrice.formatCurrency(), fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
                }
                Text(results.shortDescription ?: "", modifier = Modifier.padding(bottom = 16.dp))
                Button(onClick = {
                    Toast.makeText(ctx, R.string.add_to_cart_message, Toast.LENGTH_SHORT).show()
                }) {
                    Text(stringResource(R.string.add_to_cart_cta))
                }
            }
                else -> {
                    val exception = (uiState as ProductUiState.Error).throwable
                    Text(stringResource(R.string.details_fetch_error))
                }
        }
    }
}