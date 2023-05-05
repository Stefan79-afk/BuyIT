package com.example.buyit.service

import com.example.buyit.model.Fan
import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.FanRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class FanService (
    private val fanRepository: FanRepository
) {

    fun getFanRecommendations(fanBudget: Double, filterObject: PCRequest): List<Fan> {
        val fanQueryObject = Fan()

        fanQueryObject.priceUSD = fanBudget

        when(filterObject.pcFanNoise) {
            "silent" -> {
                fanQueryObject.noiseLevel = 25
            }

            "quiet" -> {
                fanQueryObject.noiseLevel = 40
            }

            null -> {
                fanQueryObject.noiseLevel = null
            }
        }

        when(filterObject.pcUseCase) {
            "work" -> {
                fanQueryObject.rpm = 1000
            }
            "gaming" -> {
                fanQueryObject.rpm = 1500
            }
            "studio" -> {
                fanQueryObject.rpm = 1800
            }

            "power" -> {
                fanQueryObject.rpm = 2200
            }
        }

        if(filterObject.pcFanAmount != null) {
            fanQueryObject.priceUSD /= filterObject.pcFanAmount
        }

        var queryResult = this.queryFanCollection(fanQueryObject, QueryType.QUIZ)

        if(queryResult.isEmpty()) {
            if(filterObject.pcFanAmount != null) {
                do {
                    for(i in filterObject.pcFanAmount downTo 1) {

                        fanQueryObject.priceUSD = (fanQueryObject.priceUSD * (i + 1)) / i
                        queryResult = this.queryFanCollection(fanQueryObject, QueryType.QUIZ)

                        if(queryResult.isNotEmpty()) {
                            queryResult.forEach{
                                it.amount = i
                            }

                            return queryResult
                        }

                        if(queryResult.isEmpty() && i == 1) {
                            fanQueryObject.noiseLevel = null
                            fanQueryObject.rpm = 0

                            do {
                                for(j in filterObject.pcFanAmount downTo  1) {
                                    fanQueryObject.priceUSD = (fanQueryObject.priceUSD * (j + 1)) / j
                                    queryResult = this.queryFanCollection(fanQueryObject, QueryType.QUIZ)

                                    if(queryResult.isNotEmpty()) {
                                        queryResult.forEach {
                                            it.amount = j
                                        }
                                    }

                                    if(queryResult.isEmpty() && j == 1) {
                                        return listOf()
                                    }
                                }
                            }while(queryResult.isEmpty())
                        }
                    }
                }while(true)
            }

            else {
                fanQueryObject.noiseLevel = null
                fanQueryObject.rpm = 0
                queryResult = this.queryFanCollection(fanQueryObject, QueryType.QUIZ)

                if(queryResult.isEmpty()) {
                    return queryResult
                } else {
                    queryResult.forEach{
                        it.amount = 1
                    }
                    return queryResult
                }
            }
        }

        queryResult.forEach{
            if(filterObject.pcFanAmount != null)
                it.amount = filterObject.pcFanAmount
            else
                it.amount = 1
        }
        return queryResult
    }

    private fun queryFanCollection(fanQueryObject: Fan, queryType: QueryType): List<Fan> {
        when(queryType) {
            QueryType.QUIZ -> {
                val pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))
                if(fanQueryObject.noiseLevel == null && fanQueryObject.rpm == 0) {
                    return this.fanRepository.findByPriceUSDLessThanEqual(fanQueryObject.priceUSD, pageRequest)
                } else if(fanQueryObject.noiseLevel == null && fanQueryObject.rpm != 0) {
                    return this.fanRepository.findByRpmGreaterThanEqualAndPriceUSDLessThanEqual(
                        fanQueryObject.rpm, fanQueryObject.priceUSD, pageRequest
                    )
                } else {
                    return this.fanRepository.findByNoiseLevelLessThanEqualAndRpmGreaterThanEqualAndPriceUSDLessThanEqual(fanQueryObject.noiseLevel!!, fanQueryObject.rpm, fanQueryObject.priceUSD, pageRequest)
                }
            }

            else -> return listOf()
        }
    }
}