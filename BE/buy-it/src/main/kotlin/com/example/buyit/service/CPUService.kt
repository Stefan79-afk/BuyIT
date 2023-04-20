package com.example.buyit.service

import com.example.buyit.model.CPU
import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.CPURepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CPUService(
    private val cpuRepository: CPURepository
) {
    fun getCPURecommendations(cpuBudget: Double, filterObject: PCRequest): List<Any>? {
        when (filterObject.pcUseCase) {
            "work" -> return getCPURecommendationsWork(cpuBudget, filterObject)
            "gaming" -> return getCpuRecommendationsGaming(cpuBudget, filterObject)
            "studio" -> return getCpuRecommendationsStudio(cpuBudget, filterObject)
            "power" -> return getCpuRecommendationsPower(cpuBudget, filterObject)
        }

        return listOf()
    }

    private fun getCPURecommendationsWork(cpuBudget: Double, filterObject: PCRequest): List<CPU> {
        val cpuQueryObject = CPU()

        cpuQueryObject.priceUSD = cpuBudget
        cpuQueryObject.coreCount = "2"
        cpuQueryObject.coreClock = "2.0 GHz"

        if (filterObject.pcWorkMultitask == true) {
            cpuQueryObject.coreCount = "4"
        }

        if (filterObject.pcWorkProgramming == true || filterObject.pcWorkVirtualization == true) {
            cpuQueryObject.coreCount = "4"
            cpuQueryObject.coreClock = "2.5 GHz"
        }

        val queryResult = this.queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_WORK)

        if(queryResult.isEmpty()) {
            cpuQueryObject.coreCount = "2"
            cpuQueryObject.coreClock = "2.0 GHz"

            return this.queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_WORK)
        }

        return queryResult
    }

    private fun getCpuRecommendationsGaming(cpuBudget: Double, filterObject: PCRequest): List<CPU> {
        val cpuQueryObject = CPU()

        cpuQueryObject.priceUSD = cpuBudget
        cpuQueryObject.coreCount = "2"
        cpuQueryObject.coreClock = "3.0 GHz"

        if(filterObject.pcGamingWantStreaming == true) {
            cpuQueryObject.coreCount = "4"
            cpuQueryObject.coreClock = "3.0 GHz"
        }

        if(filterObject.pcGamingRayTracingGPU == true) {
            cpuQueryObject.coreCount = "6"
            cpuQueryObject.coreClock = "3.5 GHz"
        }

        if(filterObject.pcGamingGraphicsOrPerformance == "both") {
            cpuQueryObject.coreCount = "8"
            cpuQueryObject.coreClock = "3.5 GHz"
        }

       val queryResult =  queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_GAMING)

        if(queryResult.isEmpty()) {
            cpuQueryObject.coreCount = "2"
            cpuQueryObject.coreClock = "3.0 GHz"
            return queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_GAMING)
        }

        return queryResult

    }

    private fun getCpuRecommendationsStudio(cpuBudget: Double, filterObject: PCRequest): List<CPU> {
        val cpuQueryObject = CPU()

        cpuQueryObject.priceUSD = cpuBudget
        cpuQueryObject.coreCount = "4"
        cpuQueryObject.coreClock = "3.0 GHz"

        if(filterObject.pcCreativeMostDemandingTask == "3D" || filterObject.pcCreativeMultitask == true) {
            cpuQueryObject.coreCount = "6"
            cpuQueryObject.coreClock = "3.0 GHz"
        }

        if(filterObject.pcCreativeNoLoadingTimes == true) {
            cpuQueryObject.coreCount = "8"
            cpuQueryObject.coreClock = "3.5 GHz"
        }


        val queryResult = this.queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_STUDIO)

        if(queryResult.isEmpty()) {
            cpuQueryObject.coreCount = "4"
            cpuQueryObject.coreClock = "3.0 GHz"

            return this.queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_STUDIO)
        }

        return queryResult
    }

    private fun getCpuRecommendationsPower(cpuBudget: Double, filterObject: PCRequest): List<CPU> {
        val cpuQueryObject = CPU()

        cpuQueryObject.priceUSD = cpuBudget
        cpuQueryObject.coreCount = "6"
        cpuQueryObject.coreClock = "3 GHz"

        when (filterObject.pcIntensiveMostDemandingTask) {
            "crypto_mining", "data" -> {
                cpuQueryObject.coreClock = "3.5 GHz"
            }

            "ai", "science" -> {
                cpuQueryObject.coreCount = "8"
                cpuQueryObject.coreClock = "3.5 GHz"
            }
        }

        if(filterObject.pcIntensiveOverclock == true) {
            cpuQueryObject.name = ""
        }

        if(filterObject.pcIntensiveBestPerformance == true) {
            when (filterObject.pcIntensiveMostDemandingTask) {
                "server" -> {
                    cpuQueryObject.coreCount = "8"
                    cpuQueryObject.coreClock = "3.5 GHz"
                }
                "crypto_mining", "data" -> {
                    cpuQueryObject.coreCount = "12"
                    cpuQueryObject.coreClock = "3.5 GHz"
                }

                "ai", "science" -> {
                    cpuQueryObject.coreCount = "16"
                    cpuQueryObject.coreClock = "3.5 GHz"
                }
            }
        }

        val queryResult = this.queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_POWER)

        if(queryResult.isEmpty()) {
            cpuQueryObject.coreCount = "6"
            cpuQueryObject.coreClock = "3 GHz"

            return this.queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_POWER)
        }

        return queryResult

    }

    private fun queryCPUCollection(cpuFilterObject: CPU, cpuQueryType: CPUQueryType): List<CPU> {
        when(cpuQueryType) {
            CPUQueryType.QUIZ_WORK -> {
                val pageRequest: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))

                return this.cpuRepository.findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
                    cpuFilterObject.coreCount, cpuFilterObject.coreClock, cpuFilterObject.priceUSD, pageRequest
                )
            }

             CPUQueryType.QUIZ_GAMING, CPUQueryType.QUIZ_STUDIO -> {
                val pageRequest: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))

                return this.cpuRepository.findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqual(
                    cpuFilterObject.coreCount, cpuFilterObject.coreClock,cpuFilterObject.priceUSD, pageRequest
                )
            }

            CPUQueryType.QUIZ_POWER -> {
                val pageRequest: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))

                return if(cpuFilterObject.name == "") {
                    this.cpuRepository.findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqualAndNameContainingOrCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqualAndNameContaining(
                        cpuFilterObject.coreCount, cpuFilterObject.coreClock, cpuFilterObject.priceUSD, "AMD", cpuFilterObject.coreCount, cpuFilterObject.coreClock, cpuFilterObject.priceUSD, "K", pageRequest
                    )
                } else {
                    this.cpuRepository.findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqual(
                        cpuFilterObject.coreCount, cpuFilterObject.coreClock, cpuFilterObject.priceUSD, pageRequest
                    )
                }
            }
        }
    }
}