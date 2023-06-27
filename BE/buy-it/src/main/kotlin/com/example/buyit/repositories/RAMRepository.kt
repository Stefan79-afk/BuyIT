package com.example.buyit.repositories

import com.example.buyit.model.RAM
import jdk.jfr.Frequency
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface RAMRepository: MongoRepository<RAM, String> {
    fun findByCapacityGreaterThanEqualAndTypeGreaterThanEqualAndModulesContainingAndPriceUSDLessThanEqual(
        capacity: Int, type: String, modules: String, priceUSD: Double, pageable: Pageable): List<RAM>

    fun findByCapacityGreaterThanEqualAndTypeGreaterThanEqualAndModulesGreaterThanEqualAndPriceUSDLessThanEqual(
        capacity: Int, type: String, modules: String, priceUSD: Double, pageable: Pageable): List<RAM>

}