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
        }

        return listOf();
    }

    private fun getCPURecommendationsWork(cpuBudget: Double, filterObject: PCRequest): List<CPU> {
        var cpuQueryObject: CPU = CPU();

        cpuQueryObject.priceUSD = cpuBudget;
        cpuQueryObject.integratedGraphics = "";

        if(filterObject.pcWorkMultitask == true) {
            cpuQueryObject.coreCount = "4";
            cpuQueryObject.coreClock = "3 GHz"
            cpuQueryObject.boostClock = "4 GHz"

        } else if(filterObject.pcWorkMultitask == false) {
            cpuQueryObject.coreCount = "2";
            cpuQueryObject.coreClock = "2.5 GHz"
            cpuQueryObject.boostClock = "3.5 GHz"
        }

        if(filterObject.pcWorkProgramming == true) {
            cpuQueryObject.coreClock = "3 GHz";
            cpuQueryObject.coreCount= "4";
            cpuQueryObject.boostClock = "4 GHz"
        }

        if(filterObject.pcWorkVirtualization == true) {
            cpuQueryObject.coreClock = "3 Ghz";
            cpuQueryObject.coreCount = "6"
            cpuQueryObject.boostClock = "5 GHz"
        }


        return queryCPUCollection(cpuQueryObject, CPUQueryType.QUIZ_WORK).toList();
    }

    private fun queryCPUCollection(cpuFilterObject: CPU, cpuQueryType: CPUQueryType): Collection<CPU> {
        when(cpuQueryType) {
            CPUQueryType.QUIZ_WORK -> cpuFilterObject.priceUSD?.let {
                return this.cpuRepository.findByCoreCountGreaterThanEqualAndCoreClockGreaterThanAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
                    cpuFilterObject.coreCount, cpuFilterObject.coreClock, it, Pageable.ofSize(5)
                )
            }
        }

        return listOf();
    }
}