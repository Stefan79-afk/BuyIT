package com.example.buyit.service

import com.example.buyit.model.PCRequest
import com.example.buyit.model.RAM
import com.example.buyit.repositories.RAMRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class RAMService (
    private val ramRepository: RAMRepository
) {

    fun getRamRecommendations(ramBudget: Double, filterObject: PCRequest): List<RAM> {
        when (filterObject.pcUseCase) {
            "work" -> {
                return getRamReccommendationsWork(ramBudget, filterObject)
            }

            "gaming" -> {
                return getRAMReccommendationsGaming(ramBudget, filterObject)
            }

            "studio" -> {
                return getRAMReccommendationsStudio(ramBudget, filterObject)
            }

            "power" -> {
                return  getRAMReccommendationsPower(ramBudget, filterObject)
            }

            else -> {
                return listOf()
            }
        }
    }

    private fun getRamReccommendationsWork(ramBudget: Double, filterObject: PCRequest): List<RAM> {
        val ramQueryObject = RAM();

        ramQueryObject.priceUSD = ramBudget
        ramQueryObject.capacity = 4
        ramQueryObject.type = "DDR3"
        //ramQueryObject.frequency = 1600
        ramQueryObject.modules = "1 x"

        if(filterObject.pcWorkMultitask == true) {
            ramQueryObject.capacity = 8
            ramQueryObject.type = "DDR4"
            //ramQueryObject.frequency = 2400
            ramQueryObject.modules = "2 x"
        }

        if(filterObject.pcWorkProgramming == true || filterObject.pcWorkVirtualization == true) {
            ramQueryObject.capacity = 16
            ramQueryObject.type = "DDR4"
            //ramQueryObject.frequency = 3200
            ramQueryObject.modules = "2 x"
        }

        val queryResult = this.queryRAMCollection(ramQueryObject, QueryType.QUIZ)

        if(queryResult.isEmpty()) {
            ramQueryObject.capacity = 4
            ramQueryObject.type = "DDR3"
            //ramQueryObject.frequency = 1600
            ramQueryObject.modules = "1 x"

            return this.queryRAMCollection(ramQueryObject, QueryType.QUIZ)
        }

        return queryResult
    }

    private fun getRAMReccommendationsGaming(ramBudget: Double, filterObject: PCRequest): List<RAM> {
        val ramQueryObject = RAM()

        ramQueryObject.priceUSD = ramBudget
        ramQueryObject.capacity = 8
        //ramQueryObject.frequency = 2400
        ramQueryObject.type = "DDR4"
        ramQueryObject.modules = "2 x"

        if(filterObject.pcMonitorResolution == "1920x1080" || filterObject.pcGamingGraphicsOrPerformance == "performance") {
            ramQueryObject.capacity = 8
            //ramQueryObject.frequency = 2400
            ramQueryObject.type = "DDR4"
            ramQueryObject.modules = "2 x"
        }

        if(filterObject.pcGamingWantStreaming == true || filterObject.pcGamingGraphicsOrPerformance == "graphics" || filterObject.pcGamingLatestGames == true || filterObject.pcGamingRayTracingGPU == true || filterObject.pcMonitorResolution == "2560x1440") {
            ramQueryObject.capacity = 16
            //ramQueryObject.frequency = 3000
            ramQueryObject.type = "DDR4"
            ramQueryObject.modules = "2 x"
        }

        if(filterObject.pcGamingGraphicsOrPerformance == "both" || filterObject.pcMonitorResolution == "3840x2160") {
            ramQueryObject.capacity = 32
            //ramQueryObject.frequency = 3200
            ramQueryObject.type = "DDR4"
            ramQueryObject.modules = "2 x"
        }

        val queryResult = queryRAMCollection(ramQueryObject, QueryType.QUIZ)

        if(queryResult.isEmpty()) {
            ramQueryObject.capacity = 8
            //ramQueryObject.frequency = 2400
            ramQueryObject.type = "DDR4"
            ramQueryObject.modules = "2 x"

            return queryRAMCollection(ramQueryObject, QueryType.QUIZ)
        }

        return queryResult
    }

    private fun getRAMReccommendationsStudio(ramBudget: Double, filterObject: PCRequest): List<RAM> {
        val ramQueryObject = RAM()

        ramQueryObject.priceUSD = ramBudget
        ramQueryObject.capacity = 16
        //ramQueryObject.frequency = 2400
        ramQueryObject.type = "DDR4"
        ramQueryObject.modules = "2 x"

        if(filterObject.pcCreativeMostDemandingTask == "video" || filterObject.pcCreativeNoLoadingTimes == true || filterObject.pcCreativeMultitask == true) {
            ramQueryObject.capacity = 32
            //ramQueryObject.frequency = 3000
            ramQueryObject.type = "DDR4"
            ramQueryObject.modules = "2 x"
        }

        if(filterObject.pcCreativeMostDemandingTask == "3D") {
            ramQueryObject.capacity = 64
            //ramQueryObject.frequency = 3200
            ramQueryObject.type = "DDR4"
            ramQueryObject.modules = "4 x"
        }

        val queryResult = queryRAMCollection(ramQueryObject, QueryType.QUIZ)

        if(queryResult.isEmpty()) {
            ramQueryObject.capacity = 16
            //ramQueryObject.frequency = 2400
            ramQueryObject.type = "DDR4"
            ramQueryObject.modules = "2 x"

            return queryRAMCollection(ramQueryObject, QueryType.QUIZ)
        }

        return queryResult
    }

    private fun getRAMReccommendationsPower(ramBudget: Double, filterObject: PCRequest): List<RAM> {
        val ramQueryObject = RAM()

        ramQueryObject.priceUSD = ramBudget
        ramQueryObject.capacity = 16
        //ramQueryObject.frequency = 3000
        ramQueryObject.type = "DDR4"
        ramQueryObject.modules = "2 x"

        if(filterObject.pcIntensiveMostDemandingTask == "data" || filterObject.pcIntensiveMostDemandingTask == "ai" || filterObject.pcIntensiveMultitask == true) {
            ramQueryObject.capacity = 32
            //ramQueryObject.frequency = 3200
            ramQueryObject.type = "DDR4"
            ramQueryObject.modules = "2 x"
        }

        if(filterObject.pcIntensiveMostDemandingTask == "science" || filterObject.pcIntensiveBestPerformance == true) {
            ramQueryObject.capacity = 64
            //ramQueryObject.frequency = 3600
            ramQueryObject.type = "DDR4"
            ramQueryObject.modules = "4 x"
        }

        val queryResult = queryRAMCollection(ramQueryObject, QueryType.QUIZ)

        if(queryResult.isEmpty()) {
            ramQueryObject.capacity = 16
            //ramQueryObject.frequency = 3000
            ramQueryObject.type = "DDR4"
            ramQueryObject.modules = "2 x"

            return queryRAMCollection(ramQueryObject, QueryType.QUIZ)
        }

        return queryResult


    }


     fun queryRAMCollection(ramFilterObject: RAM, queryType: QueryType): List<RAM> {
        val pageRequest: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "capacity"))

         return when(queryType) {
             QueryType.QUIZ -> {
                 this.ramRepository.findByCapacityGreaterThanEqualAndTypeGreaterThanEqualAndModulesGreaterThanEqualAndPriceUSDLessThanEqual(
                     ramFilterObject.capacity, ramFilterObject.type, ramFilterObject.modules, ramFilterObject.priceUSD, pageRequest
                 )
             }

             else -> {
                 listOf()
             }
         }
    }
}