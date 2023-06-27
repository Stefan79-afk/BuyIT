package com.example.buyit.service

import com.example.buyit.model.PCRequest
import com.example.buyit.model.WifiCard
import com.example.buyit.repositories.WifiCardRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class WifiCardService (
    private val wifiCardRepository: WifiCardRepository
){

    fun getWifiCardRecommendations(wifiCardBudget: Double, filterObject: PCRequest): List<WifiCard> {
        val wifiCardQueryObject = WifiCard()

        wifiCardQueryObject.priceUSD = wifiCardBudget

        if(filterObject.pcWorkFastInternet == true || filterObject.pcGamingFastInternet == true || filterObject.pcCreativeFastInternet == true) {
            wifiCardQueryObject.protocol = "Wi-Fi 5"
            wifiCardQueryObject.wifiCardInterface = "PCIe x1"
        }

        val queryResult = this.queryWifiCardCollection(wifiCardQueryObject, QueryType.QUIZ)

        if(queryResult.isEmpty()) {
            wifiCardQueryObject.wifiCardInterface = ""
            wifiCardQueryObject.protocol = ""

            return this.queryWifiCardCollection(wifiCardQueryObject, QueryType.QUIZ)
        }

        return queryResult
    }

    fun queryWifiCardCollection(wifiCardQueryObject: WifiCard, queryType: QueryType): List<WifiCard> {
        return when(queryType) {
            QueryType.QUIZ -> {
                val pageRequest: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))
                if(wifiCardQueryObject.protocol != "" && wifiCardQueryObject.wifiCardInterface != "") {
                    this.wifiCardRepository.findByProtocolGreaterThanEqualAndWifiCardInterfaceAndPriceUSDLessThanEqual(
                        wifiCardQueryObject.protocol, wifiCardQueryObject.wifiCardInterface, wifiCardQueryObject.priceUSD, pageRequest
                    )
                } else {
                    this.wifiCardRepository.findByPriceUSDLessThanEqual(
                        wifiCardQueryObject.priceUSD, pageRequest)
                }
            }

            else -> listOf()
        }
    }
}