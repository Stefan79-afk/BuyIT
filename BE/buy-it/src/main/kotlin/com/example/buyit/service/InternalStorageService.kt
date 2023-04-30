package com.example.buyit.service

import com.example.buyit.model.InternalStorage
import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.InternalStorageRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class InternalStorageService (
    private val internalStorageRepository: InternalStorageRepository
) {

    fun getInternalStorageReccommendations(internalStorageBudget: Double, filterObject: PCRequest): List<InternalStorage> {
        return when (filterObject.pcUseCase) {
            "work" -> getInternalStorageReccommendationsWork(internalStorageBudget, filterObject)
            /*"gaming" -> getInternalStorageReccommendationsGaming(internalStorageBudget, filterObject)
            "studio" -> getInternalStorageReccommendationsStudio(internalStorageBudget, filterObject)
            "power" -> getInternalStorageReccommendationsPower(internalStorageBudget, filterObject)*/
            else -> listOf<InternalStorage>()
        }
    }

    private fun getInternalStorageReccommendationsWork(internalStorageBudget: Double, filterObject: PCRequest): List<InternalStorage> {
        val internalStorage = InternalStorage()

        internalStorage.priceUSD = internalStorageBudget

        internalStorage.capacity = 240
        internalStorage.type = ""

        if(filterObject.pcWorkLargeStorage == true) {
            internalStorage.capacity = 500
        }

        if(filterObject.pcWorkFastStorage == true) {
            internalStorage.type = "SSD"
        }

        val queryResult = this.queryInternalStorageCollection(internalStorage, InternalStorageQueryType.QUIZ_WORK)

        if(queryResult.isEmpty()) {
            internalStorage.capacity = 240
            internalStorage.type = ""

            return this.queryInternalStorageCollection(internalStorage, InternalStorageQueryType.QUIZ_WORK)
        }

        return queryResult
    }

    private fun queryInternalStorageCollection(internalStorageQueryObject: InternalStorage, internalStorageQueryType: InternalStorageQueryType): List<InternalStorage> {
        val pageRequest: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "capacity"))
        return when(internalStorageQueryType) {
            InternalStorageQueryType.QUIZ_WORK -> {
                if(internalStorageQueryObject.type == "")
                    this.internalStorageRepository.findByCapacityGreaterThanEqualAndPriceUSDLessThanEqual(
                        internalStorageQueryObject.capacity, internalStorageQueryObject.priceUSD, pageRequest
                    ) else
                    this.internalStorageRepository.findByCapacityGreaterThanEqualAndTypeAndPriceUSDLessThanEqual(
                        internalStorageQueryObject.capacity, internalStorageQueryObject.type, internalStorageQueryObject.priceUSD, pageRequest
                    )
            }

            else -> listOf()
        }
    }
}