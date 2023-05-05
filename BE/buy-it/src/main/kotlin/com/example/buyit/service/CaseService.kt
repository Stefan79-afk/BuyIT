package com.example.buyit.service

import com.example.buyit.model.Case
import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.CaseRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CaseService (
    private val caseRepository: CaseRepository
) {
    fun getCaseRecommendations(caseBudget: Double, filterObject: PCRequest): List<Case> {
        val caseQueryObject = Case()

        caseQueryObject.priceUSD = caseBudget

        when(filterObject.pcSize) {
            "small" -> caseQueryObject.type = "Mini ITX Desktop"
            "medium" -> caseQueryObject.type = "MicroATX Mid Tower"
            "big" -> caseQueryObject.type = "ATX Mid Tower"
            "large" -> caseQueryObject.type = "ATX Full Tower"
            null -> caseQueryObject.type = ""
        }

        when(filterObject.pcCaseColor) {
            "white" -> caseQueryObject.color = "White"
            "black" -> caseQueryObject.color = "Black"
            "white/black" -> caseQueryObject.color = "White / Black"
            "white/gray" -> caseQueryObject.color = "White / Gray"
            "black/red" -> caseQueryObject.color = "Black / Red"
            null -> caseQueryObject.color = ""
        }

        when (filterObject.pcCasePanel) {
            "Tinted Tempered Glass" -> caseQueryObject.sidePanelWindow = "Tinted Tempered Glass"
            "Tempered Glass" -> caseQueryObject.sidePanelWindow = "Tempered Glass"
            "Acrylic" -> caseQueryObject.sidePanelWindow = "Acrylic"
            "None" -> caseQueryObject.sidePanelWindow = null
            null -> caseQueryObject.sidePanelWindow = ""
        }

        val queryResult = this.queryCaseCollection(caseQueryObject, QueryType.QUIZ)

        if(queryResult.isEmpty()) {
            caseQueryObject.type = ""
            caseQueryObject.color = ""
            caseQueryObject.sidePanelWindow = ""

            return this.queryCaseCollection(caseQueryObject, QueryType.QUIZ)
        }

        return queryResult
    }

    private fun queryCaseCollection(caseQueryObject: Case, queryType: QueryType): List<Case> {
        when (queryType) {
            QueryType.QUIZ -> {
                val pageRequestQuiz: PageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "price_usd"))
                if(caseQueryObject.type == "") {
                    if(caseQueryObject.color == "") {
                        if(caseQueryObject.sidePanelWindow == "") {
                            return this.caseRepository.findByPriceUSDLessThanEqual(caseQueryObject.priceUSD, pageRequestQuiz)
                        } else {

                            if(caseQueryObject.sidePanelWindow == null) {
                                return this.caseRepository.findBySidePanelWindowNullAndPriceUSDLessThan(
                                    caseQueryObject.priceUSD,
                                    pageRequestQuiz
                                )
                            } else {
                                val sidePanelWindow: String = caseQueryObject.sidePanelWindow!!
                                return this.caseRepository.findBySidePanelWindowAndPriceUSDLessThanEqual(
                                    sidePanelWindow,
                                    caseQueryObject.priceUSD,
                                    pageRequestQuiz
                                )

                            }
                        }
                    } else {
                        if(caseQueryObject.sidePanelWindow == "") {
                            return this.caseRepository.findByColorAndPriceUSDLessThanEqual(caseQueryObject.color, caseQueryObject.priceUSD, pageRequestQuiz)
                        } else {
                            if(caseQueryObject.sidePanelWindow == null) {
                                return this.caseRepository.findByColorAndSidePanelWindowNullAndPriceUSDLessThanEqual(
                                    caseQueryObject.color, caseQueryObject.priceUSD, pageRequestQuiz
                                )
                            }

                            else {
                                val sidePanelWindow: String = caseQueryObject.sidePanelWindow!!

                                return this.caseRepository.findByColorAndSidePanelWindowAndPriceUSDLessThanEqual(
                                    caseQueryObject.color, sidePanelWindow, caseQueryObject.priceUSD, pageRequestQuiz
                                )
                            }
                        }
                    }
                } else {
                    if(caseQueryObject.color == "") {
                        if(caseQueryObject.sidePanelWindow == "") {
                            return this.caseRepository.findByTypeAndPriceUSDLessThanEqual(
                                caseQueryObject.type, caseQueryObject.priceUSD, pageRequestQuiz
                            )
                        }

                        else {
                            if(caseQueryObject.sidePanelWindow == null) {
                                return this.caseRepository.findByTypeAndSidePanelWindowNullAndPriceUSDLessThanEqual(
                                    caseQueryObject.type, caseQueryObject.priceUSD, pageRequestQuiz
                                )
                            }

                            else {
                                val sidePanelWindow = caseQueryObject.sidePanelWindow!!
                                return this.caseRepository.findByTypeAndSidePanelWindowAndPriceUSDLessThanEqual(
                                    caseQueryObject.type, sidePanelWindow, caseQueryObject.priceUSD, pageRequestQuiz
                                )
                            }
                        }
                    } else {
                        if(caseQueryObject.sidePanelWindow == "") {
                            return this.caseRepository.findByTypeAndColorAndPriceUSDLessThanEqual(
                                caseQueryObject.type, caseQueryObject.color, caseQueryObject.priceUSD, pageRequestQuiz
                            )
                        } else {
                            if(caseQueryObject.sidePanelWindow == null) {
                                return this.caseRepository.findByTypeAndColorAndSidePanelWindowNullAndPriceUSDLessThanEqual(
                                    caseQueryObject.type, caseQueryObject.color, caseQueryObject.priceUSD, pageRequestQuiz
                                )
                            } else {
                                val sidePanelWindow = caseQueryObject.sidePanelWindow!!

                                return this.caseRepository.findByTypeAndColorAndSidePanelWindowAndPriceUSDLessThanEqual(
                                    caseQueryObject.type, caseQueryObject.color, sidePanelWindow, caseQueryObject.priceUSD, pageRequestQuiz
                                )
                            }
                        }
                    }
                }
            }

            else -> return listOf()
        }
    }
}