package com.example.buyit.service

import com.example.buyit.model.InternalStorage
import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.InternalStorageRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class InternalStorageService(
    private val internalStorageRepository: InternalStorageRepository
) {

    fun getInternalStorageReccommendations(
        internalStorageBudget: Double,
        filterObject: PCRequest
    ): List<InternalStorage> {
        return when (filterObject.pcUseCase) {
            "work" -> getInternalStorageRecommendationsWork(internalStorageBudget, filterObject)
            "gaming" -> getInternalStorageRecommendationsGaming(internalStorageBudget, filterObject)
            "studio" -> getInternalStorageRecommendationsStudio(internalStorageBudget, filterObject)
            "power" -> getInternalStorageRecommendationsPower(internalStorageBudget, filterObject)
            else -> listOf<InternalStorage>()
        }
    }

    private fun getInternalStorageRecommendationsWork(
        internalStorageBudget: Double,
        filterObject: PCRequest
    ): List<InternalStorage> {
        val internalStorage = InternalStorage()

        internalStorage.priceUSD = internalStorageBudget

        internalStorage.capacity = 240
        internalStorage.type = "SSD"

        if (filterObject.pcWorkLargeStorage == true) {
            internalStorage.capacity = 500
        }

        if (filterObject.pcWorkFastStorage == true) {
            internalStorage.cache = 0
        }

        val queryResult = this.queryInternalStorageCollection(internalStorage, QueryType.QUIZ)

        if (queryResult.isEmpty()) {
            internalStorage.capacity = 240
            internalStorage.type = ""
            internalStorage.cache = null

            return this.queryInternalStorageCollection(internalStorage, QueryType.QUIZ)
        }

        return queryResult
    }

    private fun getInternalStorageRecommendationsGaming(
        internalStorageBudget: Double,
        filterObject: PCRequest
    ): List<InternalStorage> {
        val internalStorageQueryObject = InternalStorage()

        internalStorageQueryObject.priceUSD = internalStorageBudget

        internalStorageQueryObject.capacity = 500
        internalStorageQueryObject.type = "SSD"

        if (filterObject.pcGamingLargeStorage == true) {
            internalStorageQueryObject.capacity = 1000
        }

        val queryResult =
            this.queryInternalStorageCollection(internalStorageQueryObject, QueryType.QUIZ)

        if (queryResult.isEmpty()) {
            internalStorageQueryObject.capacity = 500
            internalStorageQueryObject.type = ""

            return this.queryInternalStorageCollection(internalStorageQueryObject, QueryType.QUIZ)
        }

        return queryResult
    }

    private fun getInternalStorageRecommendationsStudio(
        internalStorageBudget: Double,
        filterObject: PCRequest
    ): List<InternalStorage> {
        val internalStorageQueryObject = InternalStorage()

        internalStorageQueryObject.priceUSD = internalStorageBudget

        internalStorageQueryObject.capacity = 500
        internalStorageQueryObject.type = "SSD"

        if (filterObject.pcCreativeLargeStorage == true) {
            internalStorageQueryObject.capacity = 1000
        }

        if (filterObject.pcCreativeStorageSpeed == true || filterObject.pcCreativeNoLoadingTimes == true) {
            internalStorageQueryObject.cache = 0
        }

        val queryResult = this.queryInternalStorageCollection(internalStorageQueryObject, QueryType.QUIZ)

        if(queryResult.isEmpty()) {
            internalStorageQueryObject.capacity = 500
            internalStorageQueryObject.type = "SSD"

            return this.queryInternalStorageCollection(internalStorageQueryObject, QueryType.QUIZ)
        }

        return queryResult


    }

    private fun getInternalStorageRecommendationsPower(
        internalStorageBudget: Double,
        filterObject: PCRequest
    ): List<InternalStorage> {

        val internalStorageQueryObject = InternalStorage()

        internalStorageQueryObject.priceUSD = internalStorageBudget

        internalStorageQueryObject.capacity = 1000
        internalStorageQueryObject.type = "SSD"

        if(filterObject.pcIntensiveLargeStorage == true) {
            internalStorageQueryObject.capacity = 2000
        }

        if(filterObject.pcIntensiveBestPerformance == true) {
            internalStorageQueryObject.cache = 0
        }

        val queryResult = this.queryInternalStorageCollection(internalStorageQueryObject, QueryType.QUIZ)

        if(queryResult.isEmpty()) {
            internalStorageQueryObject.capacity = 1000
            internalStorageQueryObject.type = "SSD"
            internalStorageQueryObject.cache = null

            return this.queryInternalStorageCollection(internalStorageQueryObject, QueryType.QUIZ)
        }

        return queryResult
    }

    private fun queryInternalStorageCollection(
        internalStorageQueryObject: InternalStorage,
        queryType: QueryType
    ): List<InternalStorage> {
        val pageRequest: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "capacity"))
        return when (queryType) {
            QueryType.QUIZ -> {
                if (internalStorageQueryObject.type == "")
                    this.internalStorageRepository.findByCapacityGreaterThanEqualAndPriceUSDLessThanEqual(
                        internalStorageQueryObject.capacity, internalStorageQueryObject.priceUSD, pageRequest
                    ) else {
                    if (internalStorageQueryObject.cache == 0)
                        this.internalStorageRepository.findByCapacityGreaterThanEqualAndTypeAndCacheNotNullAndPriceUSDLessThanEqual(
                            internalStorageQueryObject.capacity,
                            internalStorageQueryObject.type,
                            internalStorageQueryObject.priceUSD,
                            pageRequest
                        )
                    else
                        this.internalStorageRepository.findByCapacityGreaterThanEqualAndTypeAndPriceUSDLessThanEqual(
                            internalStorageQueryObject.capacity,
                            internalStorageQueryObject.type,
                            internalStorageQueryObject.priceUSD,
                            pageRequest
                        )
                }

            }

            else -> listOf()
        }
    }
}