package com.example.buyit.repositories

import com.example.buyit.model.PSU
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface PSURepository: MongoRepository<PSU, String> {
    fun findFirstByFormFactorAndPriceUSDLessThanEqual(formFactor: String, priceUSD: Double): PSU?

    fun findFirstByWattageGreaterThanEqualAndFormFactorAndPriceUSDLessThanEqual(
        wattage: Int, formFactor: String, priceUSD: Double): PSU?

    fun findFirstByWattageGreaterThanEqualAndFormFactorAndEfficiencyRatingAndPriceUSDLessThanEqual(
        wattage: Int, formFactor: String, efficiencyRating: String, priceUSD: Double): PSU?
}