package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("sound_cards")
data class SoundCard(
    @Id val id: String,
    @Field("name") val name: String,
    @Field("rating") val rating: Int,
    @Field("rating_count") val ratingCount: Int,
    @Field("price_usd") val priceUSD: Double?,
    @Field("channels") val channels: String,
    @Field("digital_audio") val digitalAudio: String?,
    @Field("snr") val snr: String?,
    @Field("sample_rate") val sampleRate: String?,
    @Field("chipset") val chipset: String?,
    @Field("interface") val soundCardInterface: String
) {
}