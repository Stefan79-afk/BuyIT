package com.example.buyit.repositories

import com.example.buyit.model.InternalStorage
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface InternalStorageRepository: MongoRepository<InternalStorage, String> {
    fun findByCapacityGreaterThanEqualAndPriceUSDLessThanEqual(
        capacity: Int, priceUSD: Double, pageable: Pageable): List<InternalStorage>

    fun findByCapacityGreaterThanEqualAndTypeAndPriceUSDLessThanEqual(
        capacity: Int, type: String, priceUSD: Double, pageable: Pageable): List<InternalStorage>
    fun findByCapacityGreaterThanEqualAndTypeAndCacheGreaterThanEqualAndPriceUSDLessThanEqual(
        capacity: Int, type: String, cache: Int, priceUSD: Double, pageable: Pageable): List<InternalStorage>
}