package com.example.buyit.repositories

import com.example.buyit.model.CPUFan
import com.example.buyit.model.Fan
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface CPUFanRepository: MongoRepository<CPUFan, String> {
    fun findByPriceUSDLessThanEqual(priceUSD: Double, pageable: Pageable): List<CPUFan>

    fun findByNoiseLevelLessThanEqualAndFanRPMGreaterThanEqualAndPriceUSDLessThanEqual(
        noiseLevel: Int, fanRPM: Int, priceUSD: Double, pageable: Pageable): List<CPUFan>

    fun findByFanRPMGreaterThanEqualAndPriceUSDGreaterThanEqual(
        fanRPM: Int, priceUSD: Double, pageable: Pageable): List<CPUFan>
}