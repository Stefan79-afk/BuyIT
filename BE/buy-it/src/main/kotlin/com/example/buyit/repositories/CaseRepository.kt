package com.example.buyit.repositories

import com.example.buyit.model.Case
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface CaseRepository : MongoRepository<Case, String> {
    fun findByPriceUSDLessThanEqual(
        priceUSD: Double, pageable: Pageable
    ): List<Case>

    fun findBySidePanelWindowAndPriceUSDLessThanEqual(
        sidePanelWindow: String, priceUSD: Double, pageable: Pageable
    ): List<Case>

    fun findBySidePanelWindowNullAndPriceUSDLessThan(
        priceUSD: Double, pageable: Pageable
    ): List<Case>

    fun findByColorAndPriceUSDLessThanEqual(
        color: String, priceUSD: Double, pageable: Pageable
    ): List<Case>

    fun findByColorAndSidePanelWindowNullAndPriceUSDLessThanEqual(
        color: String, priceUSD: Double, pageable: Pageable
    ): List<Case>

    fun findByColorAndSidePanelWindowAndPriceUSDLessThanEqual(
        color: String, sidePanelWindow: String, priceUSD: Double, pageable: Pageable
    ): List<Case>

    fun findByTypeAndPriceUSDLessThanEqual(
        type: String, priceUSD: Double, pageable: Pageable
    ): List<Case>

    fun findByTypeAndSidePanelWindowNullAndPriceUSDLessThanEqual(
        type: String, priceUSD: Double, pageable: Pageable): List<Case>

    fun findByTypeAndSidePanelWindowAndPriceUSDLessThanEqual(
        type: String, sidePanelWindow: String, priceUSD: Double, pageable: Pageable): List<Case>

    fun findByTypeAndColorAndPriceUSDLessThanEqual(
        type: String, color: String, priceUSD: Double, pageable: Pageable): List<Case>

    fun findByTypeAndColorAndSidePanelWindowNullAndPriceUSDLessThanEqual(
        type: String, color: String, priceUSD: Double, pageable: Pageable): List<Case>

    fun findByTypeAndColorAndSidePanelWindowAndPriceUSDLessThanEqual(
        type: String, color: String, sidePanelWindow: String, priceUSD: Double, pageable: Pageable): List<Case>
}