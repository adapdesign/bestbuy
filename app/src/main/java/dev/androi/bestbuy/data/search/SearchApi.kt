package dev.androi.bestbuy.data.search

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/api/v2/json/search")
    suspend fun getSearchResults(
        @Query("query") query: String = "",
        @Query("lang") lang: String = "en") : SearchResponse
}