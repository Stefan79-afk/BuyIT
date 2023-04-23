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
        gpuQueryObject.memory = "2 GB"
        gpuQueryObject.coreClock = "1500 MHz"

        if (filterObject.pcMonitorResolution == "1920x1080" || filterObject.pcGamingGraphicsOrPerformance == "performance") {
            gpuQueryObject.memory = "4 GB"
            gpuQueryObject.coreClock = "1500 MHz"
        }

        if (filterObject.pcGamingWantStreaming == true || filterObject.pcGamingGraphicsOrPerformance == "graphics" || filterObject.pcGamingLatestGames == true || filterObject.pcMonitorResolution == "2560x1440") {
            gpuQueryObject.memory = "6 GB"
            gpuQueryObject.coreClock = "1500 MHz"
        }

        if (filterObject.pcGamingRayTracingGPU == true) {
            gpuQueryObject.memory = "6 GB"
            gpuQueryObject.coreClock = "1500 MHz"
            gpuQueryObject.name = "RTX"
        }

        if (filterObject.pcMonitorResolution == "3840x2160" || filterObject.pcGamingGraphicsOrPerformance == "both") {
            gpuQueryObject.memory = "8 GB"
            gpuQueryObject.coreClock = "1800 MHz"
        }


        val queryResult: List<GPU> = this.queryGPUCollection(gpuQueryObject, GPUQueryType.QUIZ_GAMING)

        if (queryResult.isEmpty()) {
            gpuQueryObject.memory = "2 GB"
            gpuQueryObject.coreClock = "1500 MHz"
            gpuQueryObject.name = ""

            return this.queryGPUCollection(gpuQueryObject, GPUQueryType.QUIZ_GAMING)
        }

        return queryResult
    }

    private fun getGPURecommendationsStudio(gpuBudget: Double, filterObject: PCRequest): List<GPU> {
        val gpuQueryObject = GPU()

        gpuQueryObject.priceUSD = gpuBudget
        gpuQueryObject.memory = "2 GB"
        gpuQueryObject.coreClock = "700 MHz"

        when (filterObject.pcCreativeMostDemandingTask) {
            "video" -> {
                gpuQueryObject.memory = "4 GB"
                gpuQueryObject.coreClock = "1000 MHz"
            }

            "3D" -> {
                gpuQueryObject.memory = "6 GB"
                gpuQueryObject.coreClock = "1500 MHz"
            }
        }

        if (filterObject.pcCreativeNoLoadingTimes == true) {
            gpuQueryObject.memory = "8 GB"
            gpuQueryObject.coreClock = "1500 MHz"
        }

        val queryResult = this.queryGPUCollection(gpuQueryObject, GPUQueryType.QUIZ_STUDIO)

        if (queryResult.isEmpty()) {
            gpuQueryObject.memory = "2 GB"
            gpuQueryObject.coreClock = "700 MHz"

            return this.queryGPUCollection(gpuQueryObject, GPUQueryType.QUIZ_STUDIO)
        }

        return queryResult
    }

    private fun getGPURecommendationsPower(gpuBudget: Double, filterObject: PCRequest): List<GPU> {
        val gpuQueryObject = GPU()

        gpuQueryObject.priceUSD = gpuBudget
        gpuQueryObject.memory = "2 GB"
        gpuQueryObject.coreClock = "1000 MHz"


        when (filterObject.pcIntensiveMostDemandingTask) {
            "crypto_mining" -> {
                gpuQueryObject.memory = "4 GB"
                gpuQueryObject.coreClock = "1000 MHz"
            }

            "data" -> {
                gpuQueryObject.memory = "6 GB"
                gpuQueryObject.coreClock = "1500 MHz"
            }

            "ai", "science" -> {
                gpuQueryObject.memory = "8 GB"
                gpuQueryObject.coreClock = "1500 MHz"
            }
        }

        if (filterObject.pcIntensiveBestPerformance == true) {
            gpuQueryObject.memory = "8 GB"
            gpuQueryObject.coreClock = "1500 MHz"
        }

        val queryResult = this.queryGPUCollection(gpuQueryObject, GPUQueryType.QUIZ_POWER)

        if(queryResult.isEmpty()) {
            gpuQueryObject.memory = "2 GB"
            gpuQueryObject.coreClock = "1000 MHz"

            return this.queryGPUCollection(gpuQueryObject, GPUQueryType.QUIZ_POWER)
        }

        return queryResult
    }

    private fun queryGPUCollection(gpuFilterObject: GPU, gpuQueryType: GPUQueryType): List<GPU> {
        val pageRequestQuiz: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "memory"))
        when (gpuQueryType) {
            GPUQueryType.QUIZ_GAMING -> {

                return if (gpuFilterObject.name == "RTX") {
                    this.gpuRepository.findByMemoryGreaterThanEqualAndCoreClockGreaterThanEqualAndNameContainingAndPriceUSDLessThanEqual(
                        gpuFilterObject.memory,
                        gpuFilterObject.coreClock,
                        gpuFilterObject.name,
                        gpuFilterObject.priceUSD,
                        pageRequestQuiz
                    )
                } else {
                    this.gpuRepository.findByMemoryGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqual(
                        gpuFilterObject.memory, gpuFilterObject.coreClock, gpuFilterObject.priceUSD, pageRequestQuiz
                    )
                }
            }

            GPUQueryType.QUIZ_STUDIO, GPUQueryType.QUIZ_POWER -> {
                return this.gpuRepository.findByMemoryGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqual(
                    gpuFilterObject.memory, gpuFilterObject.coreClock, gpuFilterObject.priceUSD, pageRequestQuiz
                )
            }

            else -> {
                return listOf()
            }
        }
    }
}