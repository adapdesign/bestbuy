package dev.androi.bestbuy.data.search

import dev.androi.bestbuy.utils.ApiResult
interface SearchRepository {
    suspend fun search(query: String, lang: String = "en"): ApiResult<SearchResponse>
}