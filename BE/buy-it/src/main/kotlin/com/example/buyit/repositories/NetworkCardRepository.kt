package com.example.buyit.repositories

import com.example.buyit.model.NetworkCard
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface NetworkCardRepository: MongoRepository<NetworkCard, String> {

    fun findByPortsContainingAndNetworkCardInterfaceContainingAndPriceUSDLessThanEqual(
        ports: String, networkCardInterface: String, priceUSD: Double, pageable: Pageable): List<NetworkCard>

    fun findByPriceUSDLessThanEqual(
        priceUSD: Double, pageable: Pageable): List<NetworkCard>


}