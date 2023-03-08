package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("cpu_coolers")
data class CPUFan(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Int,
    @Field("price_usd") val priceUSD: Double?,
    @Field("fan_rpm") val fanRPM: String?,
    @Field("noise_level") val noiseLevel: String?,
    @Field("color") val color: String?,
    @Field("radiator_size") val radiatorSize: String?
) {
}