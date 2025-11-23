package dev.androi.bestbuy.data.details

class ProductDetailsRepositoryImpl(private val api: DetailsApi) : ProductDetailsRepository {
    override suspend fun getDetails(id: String, lang: String): ProductResponse {
        return api.getProductDetails(id, lang)
    }
}