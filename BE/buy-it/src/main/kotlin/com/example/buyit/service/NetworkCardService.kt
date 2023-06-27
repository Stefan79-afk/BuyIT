package com.example.buyit.service

import com.example.buyit.model.NetworkCard
import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.NetworkCardRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class NetworkCardService (
    private val networkCardRepository: NetworkCardRepository
){
    fun getNetworkCardRecommendations(networkCardBudget: Double, filterObject: PCRequest): List<NetworkCard> {
        val pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))
        val netWorkCardQueryObject = NetworkCard()
        netWorkCardQueryObject.priceUSD = networkCardBudget

        if(filterObject.pcWorkFastInternet == true || filterObject.pcGamingFastInternet == true || filterObject.pcCreativeFastInternet == true) {
            netWorkCardQueryObject.ports = "Gbit/s"
            netWorkCardQueryObject.networkCardInterface = "PCIe"
        }


        val queryResult: List<NetworkCard> = this.queryNetworkCardCollection(netWorkCardQueryObject, QueryType.QUIZ, pageRequest)

        if(queryResult.isEmpty()) {
            netWorkCardQueryObject.ports = ""
            netWorkCardQueryObject.networkCardInterface = ""

            return this.queryNetworkCardCollection(netWorkCardQueryObject, QueryType.QUIZ, pageRequest)
        }

        return queryResult
    }

    private fun queryNetworkCardCollection(networkCardQueryObject: NetworkCard, queryType: QueryType, pageable: Pageable): List<NetworkCard> {
        return when(queryType) {
            QueryType.QUIZ -> {
                if(networkCardQueryObject.ports != "" && networkCardQueryObject.networkCardInterface != "") {
                    this.networkCardRepository.findByPortsContainingAndNetworkCardInterfaceContainingAndPriceUSDLessThanEqual(
                        networkCardQueryObject.ports, networkCardQueryObject.networkCardInterface, networkCardQueryObject.priceUSD,pageable
                    )
                } else {
                    this.networkCardRepository.findByPriceUSDLessThanEqual(networkCardQueryObject.priceUSD, pageable)
                }
            }

            else -> listOf()
        }
    }

}