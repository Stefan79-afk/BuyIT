package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field


@Document("cpus")
data class CPU(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Int,
    @Field("price_usd") val priceUSD: Double?,
    @Field("core_count") val coreCount: String,
    @Field("core_clock") val coreClock: String,
    @Field("boost_clock") val boostClock: String?,
    @Field("tdp") val tdp: String,
    @Field("integrated_graphics") val integratedGraphics: String?,
    @Field("smt") val smt: Boolean
) {
}