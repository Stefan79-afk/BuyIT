package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("gpus")
data class GPU(
    @Id val id: String = "",
    @Field("name") var name: String = "",
    @Field("rating") val rating: Int = 0,
    @Field("rating_count") val ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("chipset") var chipset: String = "",
    @Field("memory") var memory: String = "",
    @Field("core_clock") var coreClock: String = "",
    @Field("boost_clock") var boostClock: String? = null,
    @Field("color") var color: String = "",
    @Field("length") var length: String = ""
) {
}