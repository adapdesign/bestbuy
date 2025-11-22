package dev.androi.bestbuy.data.search

interface SearchRepository {
    suspend fun search(query: String, lang: String = "en"): SearchResponse
}