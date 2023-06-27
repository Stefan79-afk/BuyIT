package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("psus")
data class PSU(
    @Id var id: String = "",
    @Field("name") var name: String = "",
    @Field("rating") var rating: Int = 0,
    @Field("rating_count") var ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("form_factor") var formFactor: String = "",
    @Field("efficiency_rating") var efficiencyRating: String? = null,
    @Field("wattage") var wattage: Int = 0,
    @Field("modular") var modular: String = "",
    @Field("color") var color: String? = null
) {
}