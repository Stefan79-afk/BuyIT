package com.example.buyit.repositories

import com.example.buyit.model.SoundCard
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface SoundCardRepository: MongoRepository<SoundCard, String> {
    fun findByChannelsAndPriceUSDLessThanEqual(
        channel: String, priceUSD: Double, pageable: Pageable): List<SoundCard>

    fun findByChannelsGreaterThanEqualAndPriceUSDLessThanEqual(
        channel: String, priceUSD: Double, pageable: Pageable): List<SoundCard>
}