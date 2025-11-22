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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.androi.bestbuy.data.details.ProductDetailsRepositoryImpl
import dev.androi.bestbuy.data.RetrofitClient
import dev.androi.bestbuy.data.search.SearchRepository
import dev.androi.bestbuy.data.search.SearchRepositoryImpl
import dev.androi.bestbuy.ui.details.ProductDetailsViewModel
import dev.androi.bestbuy.ui.search.SearchViewModel
import dev.androi.bestbuy.ui.details.ProductDetailsScreen
import dev.androi.bestbuy.ui.details.ProductDetailsViewModelFactory
import dev.androi.bestbuy.ui.search.SearchScreen
import dev.androi.bestbuy.ui.search.SearchViewModelFactory
import dev.androi.bestbuy.ui.theme.BestBuyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BestBuyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        NavHost()
                    }
                }
            }
        }
    }
}

@Composable
fun NavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "search") {
        composable("search") { backStackEntry->
            val searchRepo: SearchRepository = SearchRepositoryImpl(RetrofitClient.searchApi)
            val vm: SearchViewModel = viewModel(
                viewModelStoreOwner = backStackEntry,
                factory = SearchViewModelFactory(searchRepo)
            )
            SearchScreen(vm) {
                navController.navigate("detail/$it")
            }
        }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")!!
            val productDetailsRepo = ProductDetailsRepositoryImpl(RetrofitClient.productDetailsApi)
            val productDetailsViewModel: ProductDetailsViewModel = viewModel(
                viewModelStoreOwner = backStackEntry,
                factory = ProductDetailsViewModelFactory(id, productDetailsRepo)
            )
            LaunchedEffect(id) {
                productDetailsViewModel.getDetails(id)
            }
            ProductDetailsScreen(productDetailsViewModel) {
                navController.popBackStack()
            }
        }
    }
}

