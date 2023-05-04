package com.example.buyit.repositories

import com.example.buyit.model.Fan
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface FanRepository: MongoRepository<Fan, String> {
    fun findByPriceUSDLessThanEqual(priceUSD: Double, pageable: Pageable): List<Fan>

    fun findByNoiseLevelLessThanEqualAndPriceUSDLessThanEqual(noiseLevel: Int, priceUSD: Double, pageable: Pageable): List<Fan>
}