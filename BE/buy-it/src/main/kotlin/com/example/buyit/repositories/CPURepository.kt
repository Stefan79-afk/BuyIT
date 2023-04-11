package com.example.buyit.repositories

import com.example.buyit.model.CPU
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface CPURepository: MongoRepository<CPU, String> {
    fun findByCoreCountGreaterThanEqualAndCoreClockGreaterThanAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
        coreCount: String, coreClock: String, priceUSD: Double, pageable: Pageable
    ): List<CPU>
}