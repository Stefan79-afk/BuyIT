package com.example.buyit.repositories

import com.example.buyit.model.GPU
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface GPURepository: MongoRepository<GPU, String> {

    fun findByMemoryGreaterThanEqualAndPriceUSDLessThanEqual(
        memory: Int, priceUSD: Double, pageable: Pageable): List<GPU>

    fun findByMemoryGreaterThanEqualAndChipsetContainingAndPriceUSDLessThanEqualOrMemoryGreaterThanEqualAndNameContainingAndPriceUSDLessThanEqual(
        memory: Int, chipset: String, priceUSD: Double, memory1: Int, chipset1: String, priceUSD1: Double, pageable: Pageable): List<GPU>

}