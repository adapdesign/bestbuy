package dev.androi.bestbuy.data.details

import dev.androi.bestbuy.utils.ApiResult
interface ProductDetailsRepository {
    suspend fun getDetails(id: String, lang: String): ApiResult<ProductResponse>
}