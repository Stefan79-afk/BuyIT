package com.example.buyit.service

import com.example.buyit.model.*
import com.example.buyit.repositories.MotherboardRepository
import org.springframework.stereotype.Service

@Service
class MotherboardService (
    private val motherboardRepository: MotherboardRepository
) {

    fun getMotherboardRecommendations(motherboardBudget: Double, recommendations: List<PCReccomendation>, filterObject: PCRequest) {

        val motherBoardRecommendations = mutableListOf<Motherboard>()

        recommendations.forEach {
            val motherboardQueryObject = Motherboard()

            motherboardQueryObject.priceUSD = motherboardBudget

            if(it.cpu.name.contains("AMD")) {
                if(it.cpu.name.contains("Threadripper")) {
                    motherboardQueryObject.socketCPU = "sTRX4"
                } else {
                    motherboardQueryObject.socketCPU = "AM4"
                }
            } else {
                if(it.cpu.name.contains(Regex("""Intel \w* \w*-?\s?\D*1[0-1]\d\d\d\w*|Intel Celeron G5\d\d\d|Intel Pentium Gold G6\d\d\d"""))) {
                    motherboardQueryObject.socketCPU = "LGA1200"
                } else {
                    motherboardQueryObject.socketCPU = "LGA1151"
                }
            }

            if(it.ram.modules.contains(Regex("""1 x"""))) {
                motherboardQueryObject.memorySlots = 1
            } else if(it.ram.modules.contains(Regex("""2 x"""))) {
                motherboardQueryObject.memorySlots = 2
            } else if(it.ram.modules.contains(Regex("""4 x"""))) {
                motherboardQueryObject.memorySlots = 4
            } else if(it.ram.modules.contains(Regex("""8 x"""))) {
                motherboardQueryObject.memorySlots = 8
            }

            if(it.ram.capacity > 128) {
                motherboardQueryObject.memoryMax = 256
            } else {
                motherboardQueryObject.memoryMax = 128
            }

            when(it.case.type) {
                "Mini ITX Desktop" -> {
                    val queryResult = this.motherboardRepository.findFirstBySocketCPUAndMemorySlotsGreaterThanEqualAndMemoryMaxGreaterThanEqualAndFormFactorAndPriceUSDLessThanEqual(
                        motherboardQueryObject.socketCPU, motherboardQueryObject.memorySlots, motherboardQueryObject.memoryMax, "Mini ITX", motherboardQueryObject.priceUSD
                    )

                    if(queryResult != null) {
                        motherBoardRecommendations.add(queryResult)
                    }
                }

                "MicroATX Mid Tower" -> {
                    val queryResult = this.findFirstByFormFactors(
                        motherboardQueryObject.socketCPU, motherboardQueryObject.memorySlots, motherboardQueryObject.memoryMax, listOf("Mini ITX", "Micro ATX"), motherboardQueryObject.priceUSD
                    )

                    if(queryResult != null) {
                        motherBoardRecommendations.add(queryResult)
                    }
                }

                "ATX Mid Tower" -> {
                    val queryResult = this.findFirstByFormFactors(
                        motherboardQueryObject.socketCPU, motherboardQueryObject.memorySlots, motherboardQueryObject.memoryMax, listOf("Mini ITX", "Micro ATX", "ATX"), motherboardQueryObject.priceUSD
                    )

                    if(queryResult != null) {
                        motherBoardRecommendations.add(queryResult)
                    }
                }

                "ATX Full Tower" -> {
                    val queryResult = this.motherboardRepository.findFirstBySocketCPUAndMemorySlotsGreaterThanEqualAndMemoryMaxGreaterThanEqualAndPriceUSDLessThanEqual(
                        motherboardQueryObject.socketCPU, motherboardQueryObject.memorySlots, motherboardQueryObject.memoryMax, motherboardQueryObject.priceUSD
                    )

                    if(queryResult != null) {
                        motherBoardRecommendations.add(queryResult)
                    }
                }
            }

        }
    }

    private fun findFirstByFormFactors(socketCPU: String, memorySlots: Int, memoryMax: Int, formFactors: List<String>, priceUSD: Double): Motherboard? {
        return formFactors.map {
            this.motherboardRepository.findFirstBySocketCPUAndMemorySlotsGreaterThanEqualAndMemoryMaxGreaterThanEqualAndFormFactorAndPriceUSDLessThanEqual(
                socketCPU, memorySlots, memoryMax, it, priceUSD
            )
        }.firstOrNull()
    }
}