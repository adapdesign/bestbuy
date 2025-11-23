package dev.androi.bestbuy.data.details

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsApi {
    @GET("/api/v2/json/product/{id}")
    suspend fun getProductDetails(
        @Path("id") id: String = "",
        @Query("lang") lang: String = "en") : ProductResponse
}