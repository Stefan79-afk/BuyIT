package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("motherboards")
data class Motherboard(
    @Id val id: String = "",
    @Field("name") var name: String = "",
    @Field("rating") var rating: Int = 0,
    @Field("rating_count") var ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("socket_/_cpu") var socketCPU: String = "",
    @Field("form_factor") var formFactor: String = "",
    @Field("memory_max") var memoryMax: Int = 0,
    @Field("memory_slots") var memorySlots: Int = 0,
    @Field("color") var color: String = ""
) {
}