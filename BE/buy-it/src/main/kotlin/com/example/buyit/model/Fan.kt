package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("case_fans")
data class Fan(
    @Id var id: String = "",
    @Field("name") var name: String = "",
    @Field("rating") var rating: Int = 0,
    @Field("rating_count") var ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("size") var size: String = "",
    @Field("color") var color: String = "",
    @Field("rpm") var rpm: String? = null,
    @Field("airflow") var airflow: String? = null,
    @Field("noise_level") var noiseLevel: String? = null,
    @Field("pwm") var pwm: Boolean = false
) {
}