package com.example.buyit.repositories

import com.example.buyit.model.RAM
import jdk.jfr.Frequency
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface RAMRepository: MongoRepository<RAM, String> {
    fun findByCapacityGreaterThanEqualAndTypeGreaterThanEqualAndFrequencyGreaterThanEqualAndModulesContainingAndPriceUSDLessThanEqual(
        capacity: Int, type: String, frequency: Int, modules: String, priceUSD: Double, pageable: Pageable): List<RAM>

}