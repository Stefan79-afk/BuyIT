package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field


@Document("ram")
data class RAM(
    @Id var id: String = "",
    @Field("name") var name: String = "",
    @Field("rating") var rating: Int = 0,
    @Field("rating_count") var ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("capacity") var capacity: Int = 0,
    @Field("type") var type: String = "",
    @Field("frequency") var frequency: Int = 0,
    @Field("modules") var modules: String = "",
    @Field("price_/_gb") var pricePerGB: String? = null,
    @Field("color") var color: String? = null,
    @Field("first_word_latency") var latency: String = "",
    @Field("cas_latency") var casLatency: String = ""
) {
}