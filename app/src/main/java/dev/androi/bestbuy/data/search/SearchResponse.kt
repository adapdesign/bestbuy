package dev.androi.bestbuy.data.search

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val products: List<ProductItem>? = emptyList()
)

@JsonClass(generateAdapter = true)
data class ProductItem(
    val sku: String?,
    val name: String?,
    val thumbnailImage: String?,
    val regularPrice: Double?,
    val salePrice: Double?,
)