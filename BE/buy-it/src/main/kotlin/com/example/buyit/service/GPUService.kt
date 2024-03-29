package com.example.buyit.service

import com.example.buyit.model.GPU
import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.GPURepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class GPUService(
    private val gpuRepository: GPURepository
) {

    fun getGPURecommendations(gpuBudget: Double, filterObject: PCRequest): List<GPU> {
        when (filterObject.pcUseCase) {
            "gaming" -> return getGPURecommendationsGaming(gpuBudget, filterObject)
            "studio" -> return getGPURecommendationsStudio(gpuBudget, filterObject)
            "power" -> return getGPURecommendationsPower(gpuBudget, filterObject)
        }

        return listOf()
    }

    private fun getGPURecommendationsGaming(gpuBudget: Double, filterObject: PCRequest): List<GPU> {
        val gpuQueryObject = GPU()

        gpuQueryObject.priceUSD = gpuBudget
        gpuQueryObject.memory = 2

        if (filterObject.pcMonitorResolution == "1920x1080" || filterObject.pcGamingGraphicsOrPerformance == "performance") {
            gpuQueryObject.memory = 4
        }

        if (filterObject.pcGamingWantStreaming == true || filterObject.pcGamingGraphicsOrPerformance == "graphics" || filterObject.pcGamingLatestGames == true || filterObject.pcMonitorResolution == "2560x1440") {
            gpuQueryObject.memory = 6
        }

        if (filterObject.pcGamingRayTracingGPU == true) {
            gpuQueryObject.memory = 6
            gpuQueryObject.chipset = "ray_tracing"
        }

        if (filterObject.pcMonitorResolution == "3840x2160" || filterObject.pcGamingGraphicsOrPerformance == "both") {
            gpuQueryObject.memory = 8
        }


        val queryResult: List<GPU> = this.queryGPUCollection(gpuQueryObject, QueryType.QUIZ_GAMING)

        if (queryResult.isEmpty()) {
            gpuQueryObject.memory = 2
            gpuQueryObject.chipset = ""

            return this.queryGPUCollection(gpuQueryObject, QueryType.QUIZ_GAMING)
        }

        return queryResult
    }

    private fun getGPURecommendationsStudio(gpuBudget: Double, filterObject: PCRequest): List<GPU> {
        val gpuQueryObject = GPU()

        gpuQueryObject.priceUSD = gpuBudget
        gpuQueryObject.memory = 2
        gpuQueryObject.coreClock = 700

        when (filterObject.pcCreativeMostDemandingTask) {
            "video" -> {
                gpuQueryObject.memory = 4
                gpuQueryObject.coreClock = 1000
            }

            "3D" -> {
                gpuQueryObject.memory = 6
                gpuQueryObject.coreClock = 1500
            }
        }

        if (filterObject.pcCreativeNoLoadingTimes == true) {
            gpuQueryObject.memory = 8
            gpuQueryObject.coreClock = 1500
        }

        val queryResult = this.queryGPUCollection(gpuQueryObject, QueryType.QUIZ_STUDIO)

        if (queryResult.isEmpty()) {
            gpuQueryObject.memory = 2
            gpuQueryObject.coreClock = 700

            return this.queryGPUCollection(gpuQueryObject, QueryType.QUIZ_STUDIO)
        }

        return queryResult
    }

    private fun getGPURecommendationsPower(gpuBudget: Double, filterObject: PCRequest): List<GPU> {
        val gpuQueryObject = GPU()

        gpuQueryObject.priceUSD = gpuBudget
        gpuQueryObject.memory = 2
        gpuQueryObject.coreClock = 1000


        when (filterObject.pcIntensiveMostDemandingTask) {
            "crypto_mining" -> {
                gpuQueryObject.memory = 4
                gpuQueryObject.coreClock = 1000
            }

            "data" -> {
                gpuQueryObject.memory = 6
                gpuQueryObject.coreClock = 1500
            }

            "ai", "science" -> {
                gpuQueryObject.memory = 8
                gpuQueryObject.coreClock = 1500
            }
        }

        if (filterObject.pcIntensiveBestPerformance == true) {
            gpuQueryObject.memory = 8
            gpuQueryObject.coreClock = 1500
        }

        if(filterObject.pcIntensiveMultipleGPUs != true) {
            val queryResult = this.queryGPUCollection(gpuQueryObject, QueryType.QUIZ_POWER)

            if(queryResult.isEmpty()) {
                gpuQueryObject.memory = 2
                gpuQueryObject.coreClock = 1000

                return this.queryGPUCollection(gpuQueryObject, QueryType.QUIZ_POWER)
            }

            queryResult.forEach {
                it.amount = 1
            }

            return queryResult
        } else {
            gpuQueryObject.priceUSD /= 4

            var queryResult = this.queryGPUCollection(gpuQueryObject, QueryType.QUIZ_POWER)

            if(queryResult.isEmpty()) {
                gpuQueryObject.priceUSD = gpuQueryObject.priceUSD * 2

                queryResult = this.queryGPUCollection(gpuQueryObject, QueryType.QUIZ_POWER)

                if(queryResult.isEmpty()) {
                    gpuQueryObject.priceUSD = gpuQueryObject.priceUSD * 2

                    queryResult = this.queryGPUCollection(gpuQueryObject, QueryType.QUIZ_POWER)

                    if(queryResult.isEmpty()) {
                        gpuQueryObject.memory = 2
                        gpuQueryObject.coreClock = 1000

                        return this.queryGPUCollection(gpuQueryObject, QueryType.QUIZ_POWER)
                    }

                    queryResult.forEach {
                        it.amount = 1
                    }

                    return queryResult
                }

                queryResult.forEach {
                    it.amount = 2
                }

                return queryResult
            }

            queryResult.forEach {
                it.amount = 4
            }

            return queryResult
        }
    }

    private fun queryGPUCollection(gpuFilterObject: GPU, queryType: QueryType): List<GPU> {
        val pageRequestQuiz: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "memory"))
        when (queryType) {
            QueryType.QUIZ_GAMING -> {

                return if (gpuFilterObject.chipset == "ray_tracing") {
                    this.gpuRepository.findByMemoryGreaterThanEqualAndChipsetContainingAndPriceUSDLessThanEqualOrMemoryGreaterThanEqualAndNameContainingAndPriceUSDLessThanEqual(
                        gpuFilterObject.memory,
                        "RTX",
                        gpuFilterObject.priceUSD,
                        gpuFilterObject.memory,
                        Regex("Radeon RX 6\\d\\d\\d").toString(),
                        gpuFilterObject.priceUSD,
                        pageRequestQuiz
                    )
                } else {
                    this.gpuRepository.findByMemoryGreaterThanEqualAndPriceUSDLessThanEqual(
                        gpuFilterObject.memory, gpuFilterObject.priceUSD, pageRequestQuiz
                    )
                }
            }

            QueryType.QUIZ_STUDIO, QueryType.QUIZ_POWER -> {
                return this.gpuRepository.findByMemoryGreaterThanEqualAndPriceUSDLessThanEqual(
                    gpuFilterObject.memory, gpuFilterObject.priceUSD, pageRequestQuiz
                )
            }

            else -> {
                return listOf()
            }
        }
    }
}