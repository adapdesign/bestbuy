package dev.androi.bestbuy.data.search

class SearchRepositoryImpl(private val api: SearchApi): SearchRepository {
    override suspend fun search(query: String, lang: String): SearchResponse {
        return api.getSearchResults(query = query, lang = lang)
    }
}