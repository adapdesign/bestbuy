package dev.androi.bestbuy.data.details

import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsApi {
    @GET("/api/v2/json/product/{id}")
    suspend fun getProductDetails(
        @Path("id") id: String = "") : ProductResponse
}