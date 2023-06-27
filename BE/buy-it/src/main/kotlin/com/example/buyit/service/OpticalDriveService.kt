package com.example.buyit.service

import com.example.buyit.model.OpticalDrive
import com.example.buyit.repositories.OpticalDriveRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class OpticalDriveService (
    private val opticalDriveRepository: OpticalDriveRepository
) {

    fun getOpticalDriveRecommendations(opticalDriveBudget: Double): List<OpticalDrive> {
        val opticalDriveQueryObject = OpticalDrive()

        opticalDriveQueryObject.priceUSD = opticalDriveBudget

        return this.queryOpticalDriveCollection(opticalDriveQueryObject, QueryType.QUIZ)
    }

    private fun queryOpticalDriveCollection(opticalDriveQueryObject: OpticalDrive, queryType: QueryType): List<OpticalDrive> {
        return when(queryType) {
            QueryType.QUIZ -> {
                val pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))
                 this.opticalDriveRepository.findByPriceUSDLessThanEqual(opticalDriveQueryObject.priceUSD, pageRequest)
            }

            else -> listOf()
        }
    }
}