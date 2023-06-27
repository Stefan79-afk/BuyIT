package com.example.buyit.repositories

import com.example.buyit.model.Motherboard
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface MotherboardRepository: MongoRepository<Motherboard, String> {
    fun findFirstBySocketCPUAndMemorySlotsGreaterThanEqualAndMemoryMaxGreaterThanEqualAndFormFactorAndPriceUSDLessThanEqual(
        socketCPU: String, memorySlots: Int, memoryMax: Int, formFactor: String, priceUSD: Double): Motherboard?

    fun findFirstBySocketCPUAndMemorySlotsGreaterThanEqualAndMemoryMaxGreaterThanEqualAndPriceUSDLessThanEqual(
        socketCPU: String, memorySlots: Int, memoryMax: Int, priceUSD: Double
    ): Motherboard?


}