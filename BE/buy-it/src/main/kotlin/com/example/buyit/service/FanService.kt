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

        if(filterObject.pcFanAmount != null) {
            fanQueryObject.priceUSD /= filterObject.pcFanAmount
        }

        var queryResult = this.queryFanCollection(fanQueryObject, FanQueryType.QUIZ)

        if(queryResult.isEmpty()) {
            if(filterObject.pcFanAmount != null) {
                do {
                    for(i in filterObject.pcFanAmount downTo 1) {

                        fanQueryObject.priceUSD = (fanQueryObject.priceUSD * (i + 1)) / i
                        queryResult = this.queryFanCollection(fanQueryObject, FanQueryType.QUIZ)

                        if(queryResult.isNotEmpty()) {
                            queryResult.forEach{
                                it.amount = i
                            }

                            return queryResult
                        }

                        if(queryResult.isEmpty() && i == 1) {
                            fanQueryObject.noiseLevel = null

                            do {
                                for(j in filterObject.pcFanAmount downTo  1) {
                                    fanQueryObject.priceUSD = (fanQueryObject.priceUSD * (j + 1)) / j
                                    queryResult = this.queryFanCollection(fanQueryObject, FanQueryType.QUIZ)

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
                queryResult = this.queryFanCollection(fanQueryObject, FanQueryType.QUIZ)

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

        return queryResult
    }

    private fun queryFanCollection(fanQueryObject: Fan, fanQueryType: FanQueryType): List<Fan> {
        when(fanQueryType) {
            FanQueryType.QUIZ -> {
                val pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))
                if(fanQueryObject.noiseLevel == null) {
                    return this.fanRepository.findByPriceUSDLessThanEqual(fanQueryObject.priceUSD, pageRequest)
                } else {
                    return this.fanRepository.findByNoiseLevelLessThanEqualAndPriceUSDLessThanEqual(fanQueryObject.noiseLevel!!, fanQueryObject.priceUSD, pageRequest)
                }
            }
        }
    }
}