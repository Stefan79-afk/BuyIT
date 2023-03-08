package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("motherboards")
data class Motherboard(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Int,
    @Field("price_usd") val priceUSD: Double?,
    @Field("socket_/_cpu") val socketCPU: String,
    @Field("form_factor") val formFactor: String,
    @Field("memory_max") val memoryMax: String,
    @Field("memory_slots") val memorySlots: String,
    @Field("color") val color: String
) {
}