package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("internal_storage")
data class InternalStorage(
    @Id var id: String = "",
    @Field("name") var name: String = "",
    @Field("rating") var rating: Int = 0,
    @Field("rating_count") var ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("capacity") var capacity: Int = 0,
    @Field("price_/_gb") var priceGB: String = "",
    @Field("type") var type: String = "",
    @Field("cache") var cache: Int? = null,
    @Field("form_factor") var formFactor: String = "",
    @Field("interface") var storageInterface: String = ""
) {
}