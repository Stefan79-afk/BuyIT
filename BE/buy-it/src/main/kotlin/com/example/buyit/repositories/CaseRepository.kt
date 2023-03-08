package com.example.buyit.repositories

import com.example.buyit.model.Case
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface CaseRepository : MongoRepository<Case, String> {
    fun findByTypeAndSidePanelWindowAndPriceUSDLessThanEqualOrderByPriceUSDAsc(
        type: String?,
        sidePanelWindow: String?,
        priceUSD: Double,
        pageable: Pageable
    ): List<Case>
}