package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("cases")
data class Case(
    @Id var id: String = "",
    @Field("name") var name: String = "",
    @Field("rating") var rating: Int = 0,
    @Field("rating_count") var ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("type") var type: String = "",
    @Field("color") var color: String = "",
    @Field("power_supply") var powerSupply: String? = null,
    @Field("side_panel_window") var sidePanelWindow: String? = null,
    @Field("external_5") val externalFans: External5 = External5(),
    @Field("internal_3") val internalFans: Internal3 = Internal3()

)

data class External5(
    @Field("25\"_bays") var twentyFiveInchBays: String = ""
)

data class Internal3(
    @Field("5\"_bays") var fiveInchBays: String = ""
)