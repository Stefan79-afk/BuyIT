package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("network_cards")
data class NetworkCard(
    @Id var id: String,
    @Field("name") var name: String = "",
    @Field("rating") var rating: Int = 0,
    @Field("rating_count") var ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("interface") var networkCardInterface: String = "",
    @Field("ports") var ports: String = "",
    @Field("color") var color: String? = null
) {
}