package com.example.buyit.repositories

import com.example.buyit.model.CPU
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface CPURepository: MongoRepository<CPU, String> {

    fun findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqual(
        coreCount: String, coreClock: String, priceUSD: Double, pageable: Pageable): List<CPU>
    fun findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
        coreCount: String, coreClock: String, priceUSD: Double, pageable: Pageable): List<CPU>
    fun findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqualAndNameContainingOrCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqualAndNameContaining(
        coreCount1: String, coreClock1: String, priceUSD1: Double, name1: String, coreCount2: String, coreClock2: String, priceUSD2: Double, name2: String, pageable: Pageable): List<CPU>
}