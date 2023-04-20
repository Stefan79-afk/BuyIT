package com.example.buyit.service

import com.example.buyit.model.CPU
import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.CPURepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CPUService(
    private val cpuRepository: CPURepository
) {
    fun getCPURecommendations(cpuBudget: Double, filterObject: PCRequest): List<Any>? {
        when (filterObject.pcUseCase) {
            "work" -> return getCPURecommendationsWork(cpuBudget, filterObject)
            "gaming" -> return getCpuRecommendationsGaming(cpuBudget, filterObject);
            /*"studio" -> return getCPURecommendationsStudio(cpuBudget, filterObject);
            "power" -> return getCPURecommendationsPower(cpuBudget, filterObject);*/
        }

        return listOf()
    }

    private fun getCPURecommendationsWork(cpuBudget: Double, filterObject: PCRequest): List<CPU>? {
        val cpuQueryObject = CPU()

        cpuQueryObject.priceUSD = cpuBudget
        cpuQueryObject.coreCount = "2"
        cpuQueryObject.coreClock = "2.0 GHz"
        cpuQueryObject.boostClock = null
        cpuQueryObject.smt = false

        if (filterObject.pcWorkMultitask == true) {
            cpuQueryObject.coreCount = "4"
        }

        if (filterObject.pcWorkProgramming == true || filterObject.pcWorkVirtualization == true) {
            cpuQueryObject.coreCount = "4"
            cpuQueryObject.coreClock = "2.5 GHz"
            cpuQueryObject.boostClock = "3.0 GHz"
        }

        val queryResult = this.queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_WORK)

        if(queryResult.isNullOrEmpty()) {
            cpuQueryObject.coreCount = "2"
            cpuQueryObject.coreClock = "2.0 GHz"
            cpuQueryObject.boostClock = null

            return this.queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_WORK)
        }

        return queryResult
    }

    private fun getCpuRecommendationsGaming(cpuBudget: Double, filterObject: PCRequest): List<CPU>? {
        val cpuQueryObject = CPU();

        cpuQueryObject.priceUSD = cpuBudget
        cpuQueryObject.coreCount = "2"
        cpuQueryObject.coreClock = "3.0 GHz"
        cpuQueryObject.boostClock = "3.3 GHz"

        if(filterObject.pcGamingWantStreaming == true) {
            cpuQueryObject.coreCount = "4"
            cpuQueryObject.coreClock = "3.0 GHz"
            cpuQueryObject.boostClock = "3.7 GHz"
        }

        if(filterObject.pcGamingRayTracingGPU == true) {
            cpuQueryObject.coreCount = "6"
            cpuQueryObject.coreClock = "3.5 GHz"
            cpuQueryObject.boostClock = "4 GHz"
        }

        if(filterObject.pcGamingGraphicsOrPerformance == "both") {
            cpuQueryObject.coreCount = "8"
            cpuQueryObject.coreClock = "3.5 GHz"
            cpuQueryObject.boostClock = "4 GHz"
        }

       val queryResult =  queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_GAMING)

        if(queryResult.isNullOrEmpty()) {
            cpuQueryObject.coreCount = "2"
            cpuQueryObject.coreClock = "3.0 GHz"
            cpuQueryObject.boostClock = "3.3 GHz"
            return queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_GAMING)
        }

        return queryResult

    }

    private fun queryCPUCollection(cpuFilterObject: CPU, cpuQueryType: CPUQueryType): List<CPU>? {
        when(cpuQueryType) {
            CPUQueryType.QUIZ_WORK -> {
                val pagerequest: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))
                return if (cpuFilterObject.boostClock == null && !cpuFilterObject.smt) {
                    this.cpuRepository.findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
                        cpuFilterObject.coreCount,
                        cpuFilterObject.coreClock,
                        cpuFilterObject.priceUSD,
                        pagerequest
                    )
                } else if (!cpuFilterObject.smt) {
                    cpuFilterObject.boostClock?.let { boostClock -> this.cpuRepository.findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndBoostClockGreaterThanEqualAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
                        cpuFilterObject.coreCount, cpuFilterObject.coreClock, boostClock, cpuFilterObject.priceUSD, pagerequest) }
                } else {
                    cpuFilterObject.boostClock?.let {boostClock -> this.cpuRepository.findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndBoostClockGreaterThanEqualAndSmtTrueAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
                        cpuFilterObject.coreCount, cpuFilterObject.coreClock, boostClock, cpuFilterObject.priceUSD, pagerequest)
                    }
                }
            }
            CPUQueryType.QUIZ_GAMING -> {
                val pagerequest: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))
                return if(!cpuFilterObject.smt) {
                    cpuFilterObject.boostClock?.let { boostClock ->
                       this.cpuRepository.findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndBoostClockGreaterThanEqualAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
                           cpuFilterObject.coreCount, cpuFilterObject.coreClock, boostClock, cpuFilterObject.priceUSD, pagerequest
                       )
                     }
                } else {
                    cpuFilterObject.boostClock?.let{ boostClock -> this.cpuRepository.findByCoreCountGreaterThanEqualAndCoreClockGreaterThanEqualAndBoostClockGreaterThanEqualAndSmtTrueAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
                        cpuFilterObject.coreCount, cpuFilterObject.coreClock, boostClock, cpuFilterObject.priceUSD, pagerequest)
                    }
                }
            }
            else -> {
                return listOf()
            }
        }
    }
}