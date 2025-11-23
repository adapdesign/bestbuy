package dev.androi.bestbuy.data.details

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponse(
    val sku: String?,
    val name: String?,
    val additionalMedia: List<AdditionalMedia>,
    val shortDescription: String?,
    val regularPrice: Double?,
    val salePrice: Double?
)

data class AdditionalMedia(
    val thumbnailUrl: String?,
    val url: String?,
    val mimeType: String
)