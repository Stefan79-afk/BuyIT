package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("gpus")
data class GPU(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Double,
    @Field("price_usd") val priceUSD: Double?,
    @Field("chipset") val chipset: String,
    @Field("memory") val memory: String,
    @Field("core_clock") val coreClock: String,
    @Field("boost_clock") val boostClock: String?,
    @Field("color") val color: String,
    @Field("length") val length: String
) {
}