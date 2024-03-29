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
    fun getCPURecommendations(cpuBudget: Double, filterObject: PCRequest): List<CPU> {
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
        cpuQueryObject.coreCount = 2
        //cpuQueryObject.coreClock = 2.0

        if (filterObject.pcWorkMultitask == true) {
            cpuQueryObject.coreCount = 4
        }

        if (filterObject.pcWorkProgramming == true || filterObject.pcWorkVirtualization == true) {
            cpuQueryObject.coreCount = 4
            //cpuQueryObject.coreClock = 2.5
        }

        var queryResult = this.queryCPUCollection(cpuQueryObject, QueryType.QUIZ_WORK)

        if(queryResult.isEmpty()) {
            cpuQueryObject.coreCount = 2
            //cpuQueryObject.coreClock = 2.0

            queryResult =  this.queryCPUCollection(cpuQueryObject, QueryType.QUIZ_WORK)
        }

        return queryResult
    }

    private fun getCpuRecommendationsGaming(cpuBudget: Double, filterObject: PCRequest): List<CPU> {
        val cpuQueryObject = CPU()

        cpuQueryObject.priceUSD = cpuBudget
        cpuQueryObject.coreCount = 2
        //cpuQueryObject.coreClock = 3.0

        if(filterObject.pcGamingWantStreaming == true) {
            cpuQueryObject.coreCount = 4
            //cpuQueryObject.coreClock = 3.0
        }

        if(filterObject.pcGamingRayTracingGPU == true) {
            cpuQueryObject.coreCount = 6
            //cpuQueryObject.coreClock = 3.5
        }

        if(filterObject.pcGamingGraphicsOrPerformance == "both") {
            cpuQueryObject.coreCount = 8
            //cpuQueryObject.coreClock = 3.5
        }

       val queryResult =  queryCPUCollection(cpuQueryObject, QueryType.QUIZ_GAMING)

        if(queryResult.isEmpty()) {
            cpuQueryObject.coreCount = 2
            //cpuQueryObject.coreClock = 3.0
            return queryCPUCollection(cpuQueryObject, QueryType.QUIZ_GAMING)
        }

        return queryResult

    }

    private fun getCpuRecommendationsStudio(cpuBudget: Double, filterObject: PCRequest): List<CPU> {
        val cpuQueryObject = CPU()

        cpuQueryObject.priceUSD = cpuBudget
        cpuQueryObject.coreCount = 4
        //cpuQueryObject.coreClock = 3.0

        if(filterObject.pcCreativeMostDemandingTask == "3D" || filterObject.pcCreativeMultitask == true) {
            cpuQueryObject.coreCount = 6
            //cpuQueryObject.coreClock = 3.0
        }

        if(filterObject.pcCreativeNoLoadingTimes == true) {
            cpuQueryObject.coreCount = 8
            //cpuQueryObject.coreClock = 3.5
        }


        val queryResult = this.queryCPUCollection(cpuQueryObject, QueryType.QUIZ_STUDIO)

        if(queryResult.isEmpty()) {
            cpuQueryObject.coreCount = 4
            //cpuQueryObject.coreClock = 3.0

            return this.queryCPUCollection(cpuQueryObject, QueryType.QUIZ_STUDIO)
        }

        return queryResult
    }

    private fun getCpuRecommendationsPower(cpuBudget: Double, filterObject: PCRequest): List<CPU> {
        val cpuQueryObject = CPU()

        cpuQueryObject.priceUSD = cpuBudget
        cpuQueryObject.coreCount = 6
        ////cpuQueryObject.coreClock = 3.0

        when (filterObject.pcIntensiveMostDemandingTask) {
            "crypto_mining", "data" -> {
                cpuQueryObject.coreCount = 6
                ////cpuQueryObject.coreClock = 3.5
            }

            "ai", "science" -> {
                cpuQueryObject.coreCount = 8
                //cpuQueryObject.coreClock = 3.5
            }
        }

        if(filterObject.pcIntensiveOverclock == true) {
            cpuQueryObject.name = "overclock"
        }

        if(filterObject.pcIntensiveBestPerformance == true) {
            when (filterObject.pcIntensiveMostDemandingTask) {
                "server" -> {
                    cpuQueryObject.coreCount = 8
                    //cpuQueryObject.coreClock = 3.5
                }
                "crypto_mining", "data" -> {
                    cpuQueryObject.coreCount = 12
                    //cpuQueryObject.coreClock = 3.5
                }

                "ai", "science" -> {
                    cpuQueryObject.coreCount = 16
                    //cpuQueryObject.coreClock = 3.5
                }
            }
        }

        val queryResult = this.queryCPUCollection(cpuQueryObject, QueryType.QUIZ_POWER)

        if(queryResult.isEmpty()) {
            cpuQueryObject.coreCount = 6
            //cpuQueryObject.coreClock = 3.0

            return this.queryCPUCollection(cpuQueryObject, QueryType.QUIZ_POWER)
        }

        return queryResult

    }

    private fun queryCPUCollection(cpuFilterObject: CPU, queryType: QueryType): List<CPU> {
        val pageRequestQuiz: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "core_count"))
        when(queryType) {
            QueryType.QUIZ_WORK -> {

                return this.cpuRepository.findByCoreCountGreaterThanEqualAndPriceUSDLessThanEqualAndIntegratedGraphicsNotNull(
                    cpuFilterObject.coreCount, cpuFilterObject.priceUSD, pageRequestQuiz
                )
            }

             QueryType.QUIZ_GAMING, QueryType.QUIZ_STUDIO -> {
                

                return this.cpuRepository.findByCoreCountGreaterThanEqualAndPriceUSDLessThanEqual(
                    cpuFilterObject.coreCount, cpuFilterObject.priceUSD, pageRequestQuiz
                )
            }

            QueryType.QUIZ_POWER -> {

                return if(cpuFilterObject.name == "overclock") {
                    this.cpuRepository.findByCoreCountGreaterThanEqualAndPriceUSDLessThanEqualAndNameContainingOrCoreCountGreaterThanEqualAndPriceUSDLessThanEqualAndNameContaining(
                        cpuFilterObject.coreCount, cpuFilterObject.priceUSD, "AMD", cpuFilterObject.coreCount, cpuFilterObject.priceUSD, "K", pageRequestQuiz
                    )
                } else {
                    this.cpuRepository.findByCoreCountGreaterThanEqualAndPriceUSDLessThanEqual(
                        cpuFilterObject.coreCount, cpuFilterObject.priceUSD, pageRequestQuiz
                    )
                }
            }

            else -> return listOf()
        }

    }
}