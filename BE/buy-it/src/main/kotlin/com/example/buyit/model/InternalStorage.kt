package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("internal_storage")
data class InternalStorage(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Int,
    @Field("price_usd") val priceUSD: Double?,
    @Field("capacity") val capacity: Int,
    @Field("price_/_gb") val priceGB: String,
    @Field("type") val type: String,
    @Field("cache") val cache: Int?,
    @Field("form_factor") val formFactor: String,
    @Field("interface") val storageInterface: String
) {
}