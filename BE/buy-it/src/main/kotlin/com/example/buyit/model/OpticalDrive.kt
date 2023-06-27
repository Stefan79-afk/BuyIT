package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("optical_drives")
data class OpticalDrive(
    @Id var id: String = "",
    @Field("name") var name: String = "",
    @Field("rating") var rating: Int = 0,
    @Field("rating_count") var ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("bd") var bd: String? = null,
    @Field("dvd") var dvd: String? = null,
    @Field("cd") var cd: String? = null,
    @Field("bd_write") var bdWrite: String? = null,
    @Field("dvd_write") var dvdWrite: String? = null,
    @Field("cd_write") var cdWrite: String? = null
) {
}