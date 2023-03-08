package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("case_fans")
data class Fan(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Int,
    @Field("price_usd") val priceUSD: Double?,
    @Field("size") val size: String,
    @Field("color") val color: String,
    @Field("rpm") val rpm: String?,
    @Field("airflow") val airflow: String?,
    @Field("noise_level") val noiseLevel: String?,
    @Field("pwm") val pwm: Boolean
) {
}