package com.example.buyit.repositories

import com.example.buyit.model.CPU
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository

interface CPURepository: MongoRepository<CPU, String> {

    fun findByCoreCountGreaterThanEqualAndPriceUSDLessThanEqual(
        coreCount: Int, priceUSD: Double, pageable: Pageable): List<CPU>
    fun findByCoreCountGreaterThanEqualAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
        coreCount: Int, priceUSD: Double, pageable: Pageable): List<CPU>
    fun findByCoreCountGreaterThanEqualAndPriceUSDLessThanEqualAndNameContainingOrCoreCountGreaterThanEqualAndPriceUSDLessThanEqualAndNameContaining(
        coreCount1: Int, priceUSD1: Double, name1: String, coreCount2: Int, priceUSD2: Double, name2: String, pageable: Pageable): List<CPU>

}