package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("cases")
data class Case(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Int,
    @Field("price_usd") val priceUSD: Double?,
    @Field("type") val type: String,
    @Field("color") val color: String,
    @Field("power_supply") val powerSupply: String?,
    @Field("side_panel_window") val sidePanelWindow: String?,
    @Field("external_5") val externalFans: External5,
    @Field("internal_3") val internalFans: Internal3

)

data class External5(
    @Field("25\"_bays") val twentyFiveInchBays: String
)

data class Internal3(
    @Field("5\"_bays") val fiveInchBays: String
)