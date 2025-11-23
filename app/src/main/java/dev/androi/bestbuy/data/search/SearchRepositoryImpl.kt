package dev.androi.bestbuy.data.search

import dev.androi.bestbuy.utils.ApiResult
class SearchRepositoryImpl(private val api: SearchApi) : SearchRepository {
    override suspend fun search(query: String, lang: String): ApiResult<SearchResponse> {
        return try {
            val response = api.getSearchResults(query = query, lang = lang)
            ApiResult.Success(response)
        } catch (exception: Exception) {
            ApiResult.Failure(exception)
        }
    }
}