package com.example.buyit.service

import com.example.buyit.model.PCRecommendation
import com.example.buyit.model.PCRequest
import com.example.buyit.model.PSU
import com.example.buyit.repositories.PSURepository
import org.springframework.stereotype.Service

@Service
class PSUService (
    private val psuRepository: PSURepository
) {

    fun getPSURecommendations(psuBudget: Double, recommendations: List<PCRecommendation>, filterObject: PCRequest): List<PSU> {

        val psuRecommendations = mutableListOf<PSU>()

        recommendations.forEach {
            val psuQueryObject = PSU()

            psuQueryObject.priceUSD = psuBudget
            if(it.case.type == "Mini ITX Desktop") {
                psuQueryObject.formFactor = "SFX"
            } else {
                psuQueryObject.formFactor = "ATX"
            }

            when(filterObject.pcUseCase) {
                "work" -> psuQueryObject.wattage = 300
                "gaming" -> psuQueryObject.wattage = 500
                "studio" -> psuQueryObject.wattage = 600
                "power" -> psuQueryObject.wattage = 800
            }

            if(filterObject.pcPowerEfficiency == true) {
                psuQueryObject.efficiencyRating = ""
            }

            var queryResult = this.queryPSUCollection(psuQueryObject, QueryType.QUIZ)

            if(queryResult == null) {
                psuQueryObject.efficiencyRating = null

                queryResult = this.queryPSUCollection(psuQueryObject, QueryType.QUIZ)

                if(queryResult != null) {
                    psuRecommendations.add(queryResult)
                }
            }

            else {
                psuRecommendations.add(queryResult)
            }
        }

        return psuRecommendations
    }

    private fun findByEfficiencyRatings(wattage: Int, formFactor: String, efficiencyRatings: List<String>, priceUSD: Double): PSU? {
        efficiencyRatings.forEach{
            val queryResult = this.psuRepository.findFirstByWattageGreaterThanEqualAndFormFactorAndEfficiencyRatingAndPriceUSDLessThanEqual(
                wattage, formFactor, it, priceUSD
            )

            if(queryResult != null) {
                return queryResult
            }
        }

        return null
    }

    private fun queryPSUCollection(psuQueryObject: PSU, queryType: QueryType): PSU? {
        when(queryType) {
            QueryType.QUIZ -> {
                if(psuQueryObject.efficiencyRating != null) {
                    return this.findByEfficiencyRatings(
                        psuQueryObject.wattage,
                        psuQueryObject.formFactor,
                        listOf("80+ Bronze", "80+ Gold", "80+ Platinum", "80+ Titanium"),
                        psuQueryObject.priceUSD
                    )

                } else {
                   if(psuQueryObject.wattage != 0) {
                       return this.psuRepository.findFirstByWattageGreaterThanEqualAndFormFactorAndPriceUSDLessThanEqual(
                           psuQueryObject.wattage, psuQueryObject.formFactor, psuQueryObject.priceUSD
                       )
                   } else {
                       return this.psuRepository.findFirstByFormFactorAndPriceUSDLessThanEqual(
                           psuQueryObject.formFactor, psuQueryObject.priceUSD
                       )
                   }
                }
            }

            else -> return null
        }
    }
}