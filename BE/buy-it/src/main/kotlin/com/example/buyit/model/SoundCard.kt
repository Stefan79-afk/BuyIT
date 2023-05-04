package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("sound_cards")
data class SoundCard(
    @Id var id: String = "",
    @Field("name") var name: String = "",
    @Field("rating") var rating: Int = 0,
    @Field("rating_count") var ratingCount: Int = 0,
    @Field("price_usd") var priceUSD: Double = 0.0,
    @Field("channels") var channels: String = "",
    @Field("digital_audio") var digitalAudio: String? = null,
    @Field("snr") var snr: String? = null,
    @Field("sample_rate") var sampleRate: String? = null,
    @Field("chipset") var chipset: String? = null,
    @Field("interface") var soundCardInterface: String = ""
) {
}