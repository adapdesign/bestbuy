package dev.androi.bestbuy.data.details

import dev.androi.bestbuy.utils.ApiResult
class ProductDetailsRepositoryImpl(private val api: DetailsApi) : ProductDetailsRepository {
    override suspend fun getDetails(id: String, lang: String): ApiResult<ProductResponse> {
        return try {
            ApiResult.Success(api.getProductDetails(id, lang))
        } catch (exception: Exception) {
            ApiResult.Failure(exception)
        }
    }
}