package dev.androi.bestbuy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.androi.bestbuy.ui.details.ProductDetailsViewModel
import dev.androi.bestbuy.ui.search.SearchViewModel
import dev.androi.bestbuy.ui.details.ProductDetailsScreen
import dev.androi.bestbuy.ui.search.SearchScreen
import dev.androi.bestbuy.ui.theme.BestBuyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BestBuyTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        AppNavHost(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "search") {
        composable("search") { backStackEntry ->
            val vm: SearchViewModel = hiltViewModel(backStackEntry)
            val uiState by vm.uiState.collectAsState()
            val query by vm.query.collectAsState()
            SearchScreen(
                uiState = uiState,
                query = query,
                setQuery = { q -> vm.setQuery(q) },
                submitSearch = { vm.submitSearch() },
            ) {
                navController.navigate("detail/$it")
            }
        }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val productDetailsViewModel: ProductDetailsViewModel = hiltViewModel(backStackEntry)
            val uiState by productDetailsViewModel.uiState.collectAsState()
            val galleryIndex by productDetailsViewModel.galleryIndex.collectAsState()
            ProductDetailsScreen(
                uiState,
                galleryIndex,
                { productDetailsViewModel.setGalleryIndex(it) }) {
                navController.popBackStack()
            }
        }
    }
}

