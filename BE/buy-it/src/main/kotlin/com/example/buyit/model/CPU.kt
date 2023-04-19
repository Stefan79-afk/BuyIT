package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field


@Document("cpus")
data class CPU(
    @Id val id: String = "",
    @Field("name") val name: String = "",
    @Field("rating") val rating: Int = 0,
    @Field("rating_count") val ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("core_count") var coreCount: String = "",
    @Field("core_clock") var coreClock: String = "",
    @Field("boost_clock") var boostClock: String? = null,
    @Field("tdp") val tdp: String = "",
    @Field("integrated_graphics") var integratedGraphics: String? = null,
    @Field("smt") val smt: Boolean = false
) {
}