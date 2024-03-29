package com.example.buyit.service

import com.example.buyit.model.CPUFan
import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.CPUFanRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CPUFanService(
    private val cpuFanRepository: CPUFanRepository
) {
    
    fun getcpuFanRecommendations(cpuFanBudget: Double, filterObject: PCRequest): List<CPUFan> {
        val cpuFanQueryObject = CPUFan()
        
        cpuFanQueryObject.priceUSD = cpuFanBudget

        when(filterObject.pcFanNoise) {
            "silent" -> {
                cpuFanQueryObject.noiseLevel = 25
            }

            "quiet" -> {
                cpuFanQueryObject.noiseLevel = 40
            }

            null -> {
                cpuFanQueryObject.noiseLevel = null
            }
        }

        when(filterObject.pcUseCase) {
            "work" -> {
                cpuFanQueryObject.fanRPM = 1000
            }
            "gaming" -> {
                cpuFanQueryObject.fanRPM = 1500
            }
            "studio" -> {
                cpuFanQueryObject.fanRPM = 1800
            }

            "power" -> {
                cpuFanQueryObject.fanRPM = 2200
            }
        }

        val queryResult = this.queryCPUFanCollection(cpuFanQueryObject, QueryType.QUIZ)

        if(queryResult.isEmpty()) {
            cpuFanQueryObject.noiseLevel = null
            cpuFanQueryObject.fanRPM = 0
            return this.queryCPUFanCollection(cpuFanQueryObject, QueryType.QUIZ)
        }

        return queryResult
    }

    private fun queryCPUFanCollection(cpuFanQueryObject: CPUFan, queryType: QueryType): List<CPUFan> {
        when(queryType) {
            QueryType.QUIZ -> {
                val pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))
                if(cpuFanQueryObject.noiseLevel != null && cpuFanQueryObject.fanRPM != 0) {
                   return this.cpuFanRepository.findByNoiseLevelLessThanEqualAndFanRPMGreaterThanEqualAndPriceUSDLessThanEqual(
                       cpuFanQueryObject.noiseLevel!!, cpuFanQueryObject.fanRPM, cpuFanQueryObject.priceUSD,pageRequest )
                } else if(cpuFanQueryObject.noiseLevel == null && cpuFanQueryObject.fanRPM != 0) {
                    return this.cpuFanRepository.findByFanRPMGreaterThanEqualAndPriceUSDLessThanEqual(
                        cpuFanQueryObject.fanRPM, cpuFanQueryObject.priceUSD, pageRequest
                    )
                }
                else {
                    return this.cpuFanRepository.findByPriceUSDLessThanEqual(cpuFanQueryObject.priceUSD, pageRequest)
                }
            }

            else -> return listOf()
        }
    }
}