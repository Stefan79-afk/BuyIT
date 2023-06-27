package com.example.buyit.repositories

import com.example.buyit.model.WifiCard
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface WifiCardRepository: MongoRepository<WifiCard, String> {
    fun findByPriceUSDLessThanEqual(priceUSD: Double, pageable: Pageable): List<WifiCard>

    fun findByProtocolGreaterThanEqualAndWifiCardInterfaceAndPriceUSDLessThanEqual(
        protocol: String, wifiCardInterface: String, priceUSD: Double, pageable: Pageable): List<WifiCard>
}