package com.example.buyit.repositories

import com.example.buyit.model.CPU
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface CPURepository: MongoRepository<CPU, String> {

    fun findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqual(
        coreCount: Int, coreClock: Double, priceUSD: Double, pageable: Pageable): List<CPU>
    fun findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
        coreCount: Int, coreClock: Double, priceUSD: Double, pageable: Pageable): List<CPU>
    fun findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqualAndNameContainingOrCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqualAndNameContaining(
        coreCount1: Int, coreClock1: Double, priceUSD1: Double, name1: String, coreCount2: Int, coreClock2: Double, priceUSD2: Double, name2: String, pageable: Pageable): List<CPU>

}