package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("network_cards")
data class NetworkCard(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Int,
    @Field("price_usd") val priceUSD: Double?,
    @Field("interface") val networkCardInterface: String,
    @Field("ports") val ports: String,
    @Field("color") val color: String?
) {
}