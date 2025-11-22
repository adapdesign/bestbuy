package dev.androi.bestbuy.data.details

interface ProductDetailsRepository {
    suspend fun getDetails(id: String): ProductResponse
}