package com.example.buyit.repositories

import com.example.buyit.model.GPU
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface GPURepository: MongoRepository<GPU, String> {

    fun findByMemoryGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqual(
        memory: String, coreClock: String, priceUSD: Double, pageable: Pageable): List<GPU>

    fun findByMemoryGreaterThanEqualAndCoreClockGreaterThanEqualAndNameContainingAndPriceUSDLessThanEqual(
        memory: String, coreClock: String, name: String, priceUSD: Double, pageable: Pageable): List<GPU>

}