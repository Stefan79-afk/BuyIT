package com.example.buyit.repositories

import com.example.buyit.model.GPU
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface GPURepository: MongoRepository<GPU, String> {

    fun findByMemoryGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqual(
        memory: Int, coreClock: Int, priceUSD: Double, pageable: Pageable): List<GPU>

    fun findByMemoryGreaterThanEqualAndCoreClockGreaterThanEqualAndNameContainingAndPriceUSDLessThanEqual(
        memory: Int, coreClock: Int, name: String, priceUSD: Double, pageable: Pageable): List<GPU>

}