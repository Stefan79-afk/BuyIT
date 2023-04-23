package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field


@Document("cpus")
data class CPU(
    @Id val id: String = "",
    @Field("name") var name: String = "",
    @Field("rating") var rating: Int = 0,
    @Field("rating_count") var ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("core_count") var coreCount: Int = 0,
    @Field("core_clock") var coreClock: Double = 0.0,
    @Field("boost_clock") var boostClock: Double? = null,
    @Field("tdp") var tdp: String = "",
    @Field("integrated_graphics") var integratedGraphics: String? = null,
    @Field("smt") var smt: Boolean = false
) {
}