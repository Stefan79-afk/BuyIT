package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("optical_drive")
data class OpticalDrive(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Int,
    @Field("price_usd") val priceUSD: Double?,
    @Field("bd") val bd: String?,
    @Field("dvd") val dvd: String?,
    @Field("cd") val cd: String?,
    @Field("bd_write") val bdWrite: String?,
    @Field("dvd_write") val dvdWrite: String?,
    @Field("cd_write") val cdWrite: String?
) {
}