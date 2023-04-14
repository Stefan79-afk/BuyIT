package com.example.buyit.service

import com.example.buyit.model.CPU
import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.CPURepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CPUService(
    private val cpuRepository: CPURepository
) {
    fun getCPURecommendations(cpuBudget: Double, filterObject: PCRequest): List<CPU> {
        when (filterObject.pcUseCase) {
            "work" -> return getCPURecommendationsWork(cpuBudget, filterObject);
            "gaming" -> return getCpuRecommendationsGaming(cpuBudget, filterObject);
            /*"studio" -> return getCPURecommendationsStudio(cpuBudget, filterObject);
            "power" -> return getCPURecommendationsPower(cpuBudget, filterObject);*/
        }

        return listOf();
    }

    private fun getCPURecommendationsWork(cpuBudget: Double, filterObject: PCRequest): List<CPU> {
        val cpuQueryObject: CPU = CPU();

        if(!filterObject.pcWorkMultitask!! && !filterObject.pcWorkProgramming!! && !filterObject.pcWorkVirtualization!!) {
            cpuQueryObject.coreCount = "2"
            cpuQueryObject.coreClock = "2.5 GHz"
            cpuQueryObject.boostClock = "3 GHz"
        }

        if(filterObject.pcWorkMultitask) {
            cpuQueryObject.coreCount = "4"
            cpuQueryObject.coreClock = "2.8 GHz"
            cpuQueryObject.boostClock = "3.6 GHz"
        } else if(filterObject.pcWorkProgramming == true) {
            cpuQueryObject.coreCount = "2"
            cpuQueryObject.coreClock = "3.0 GHz"
            cpuQueryObject.boostClock = "3.6 GHz"
        } else if(filterObject.pcWorkVirtualization == true) {
            cpuQueryObject.coreCount = "4"
            cpuQueryObject.coreClock = "3.0 GHz"
            cpuQueryObject.boostClock = "3.8 GHz"
        }

        if(filterObject.pcWorkMultitask && filterObject.pcWorkProgramming == true) {
            cpuQueryObject.coreCount = "4"
            cpuQueryObject.coreClock = "3.0 GHz"
            cpuQueryObject.boostClock = "3.8 GHz"
        } else if(filterObject.pcWorkMultitask && filterObject.pcWorkVirtualization == true) {
            cpuQueryObject.coreCount = "4"
            cpuQueryObject.coreClock = "3.2 GHz"
            cpuQueryObject.boostClock = "4.0 GHz"
        } else if(filterObject.pcWorkProgramming == true && filterObject.pcWorkVirtualization == true) {
            cpuQueryObject.coreCount = "6"
            cpuQueryObject.coreClock = "3.5 GHz"
            cpuQueryObject.boostClock = "4.3 GHz"
        }

        if(filterObject.pcWorkMultitask && filterObject.pcWorkProgramming == true && filterObject.pcWorkVirtualization == true) {
            cpuQueryObject.coreCount = "8"
            cpuQueryObject.coreClock = "3.8 GHz"
            cpuQueryObject.boostClock = "4.5 GHz"
        }

        return queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_WORK).toList();
    }

    private fun getCpuRecommendationsGaming(cpuBudget: Double, filterObject: PCRequest): Collection<CPU> {
        val cpuQueryObject: CPU = CPU();

    }

    private fun queryCPUCollection(cpuFilterObject: CPU, cpuQueryType: CPUQueryType): Collection<CPU> {
        when(cpuQueryType) {
            CPUQueryType.QUIZ_WORK -> cpuFilterObject.priceUSD?.let {
                return this.cpuRepository.findByCoreCountGreaterThanEqualAndCoreClockGreaterThanAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
                    cpuFilterObject.coreCount, cpuFilterObject.coreClock, it, Pageable.ofSize(5)
                )
            }

            else -> return listOf()
        }

        return listOf();
    }
}