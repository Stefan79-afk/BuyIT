package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field


@Document("ram")
data class RAM(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Int,
    @Field("price_usd") val priceUSD: Double?,
    @Field("speed") val speed: String,
    @Field("modules") val modules: String,
    @Field("price_/_gb") val pricePerGB: String?,
    @Field("color") val color: String?,
    @Field("first_word_latency") val latency: String,
    @Field("cas_latency") val casLatency: String
) {
}