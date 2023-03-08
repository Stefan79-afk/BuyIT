package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("psus")
data class PSU(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Int,
    @Field("price_usd") val priceUSD: Double?,
    @Field("form_factor") val formFactor: String,
    @Field("efficiency_rating") val efficiencyRating: String?,
    @Field("wattage") val wattage: String,
    @Field("modular") val modular: String,
    @Field("color") val color: String?
) {
}