package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("cpu_coolers")
data class CPUFan(
    @Id var id: String = "",
    @Field("name") var name: String = "",
    @Field("rating") var rating: Int = 0,
    @Field("rating_count") var ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("fan_rpm") var fanRPM: Int = 0,
    @Field("noise_level") var noiseLevel: Int? = null,
    @Field("color") var color: String? = null,
    @Field("radiator_size") var radiatorSize: String? = null
) {
}