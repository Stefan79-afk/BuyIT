package com.example.buyit.service

import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.*
import com.example.buyit.model.*;
import org.springframework.stereotype.Service

@Service
class QuizService(
    private val caseService: CaseService,
    private val cpuFanService: CPUFanService,
    private val cpuService: CPUService,
    private val fanService: FanService,
    private val gpuService: GPUService,
    private val internalStorageService: InternalStorageService,
    private val motherboardService: MotherboardService,
    private val networkCardService: NetworkCardService,
    private val opticalDriveService: OpticalDriveService,
    private val psuService: PSUService,
    private val ramService: RAMService,
    private val soundCardService: SoundCardService,
    private val wifiCardService: WifiCardService

) {
    fun quiz(filterObject: PCRequest): List<PCReccomendation> {
        val budgetAllocation: MutableMap<String, Double> = when (filterObject.pcUseCase) {
            "work" -> divideBudgetWork(filterObject)
            "gaming" -> divideBudgetGamingAndPower(filterObject)
            "studio" -> divideBudgetCreative(filterObject)
            "power" -> divideBudgetGamingAndPower(filterObject)
            else -> mutableMapOf()
        }
            val recommendations = mutableListOf<PCReccomendation>()

            val cpuRecommendations =
                this.cpuService.getCPURecommendations(budgetAllocation.getValue("cpu"), filterObject)

            val gpuRecommendations = if (filterObject.pcUseCase == "work") null else
                this.gpuService.getGPURecommendations(budgetAllocation.getValue("gpu"), filterObject)

            val ramRecommendations =
                this.ramService.getRamRecommendations(budgetAllocation.getValue("ram"), filterObject)

            val internalStorageRecommendations =
                this.internalStorageService.getInternalStorageReccommendations(budgetAllocation.getValue("internal_storage"), filterObject)

            val caseRecommendations =
                this.caseService.getCaseRecommendations(budgetAllocation.getValue("case"), filterObject)

            val fanRecommendations =
                this.fanService.getFanRecommendations(budgetAllocation.getValue("fan"), filterObject)

            val cpuFanRecommendations =
                this.cpuFanService.getcpuFanRecommendations(budgetAllocation.getValue("cpu_fan"), filterObject)

            val soundCardRecommendations =
                this.soundCardService.getSoundCardRecommendations(budgetAllocation.getValue("sound_card"), filterObject)

            val wifiCardRecommendations =
                this.wifiCardService.getWifiCardRecommendations(budgetAllocation.getValue("wifi_card"), filterObject)

            val lists = mutableListOf<List<Any>>()

            lists.add(cpuRecommendations)
            lists.add(ramRecommendations)
            lists.add(internalStorageRecommendations)
            lists.add(caseRecommendations)
            lists.add(fanRecommendations)
            lists.add(cpuFanRecommendations)
            lists.add(soundCardRecommendations)
            lists.add(wifiCardRecommendations)

            if(gpuRecommendations != null) {
                lists.add(gpuRecommendations)
            }

            val minimumRecommendationSize = getMinimumSize(lists)



            for(i in 0 until minimumRecommendationSize) {
                if(gpuRecommendations.isNullOrEmpty()) {
                    val pcReccomendation = PCReccomendation(cpuRecommendations[i], null, ramRecommendations[i], internalStorageRecommendations[i], caseRecommendations[i], fanRecommendations[i], cpuFanRecommendations[i], soundCardRecommendations[i], wifiCardRecommendations[i])
                    recommendations.add(pcReccomendation)
                }

                else {
                    val pcReccomendation = PCReccomendation(cpuRecommendations[i], gpuRecommendations[i], ramRecommendations[i], internalStorageRecommendations[i], caseRecommendations[i], fanRecommendations[i], cpuFanRecommendations[i], soundCardRecommendations[i], wifiCardRecommendations[i])
                    recommendations.add(pcReccomendation)
                }
            }

            val motherBoardRecommendations = this.motherboardService.getMotherboardRecommendations(budgetAllocation.getValue("motherboard"), recommendations, filterObject)

            if(motherBoardRecommendations.isEmpty()) {
                return listOf()
            }

            for(i in motherBoardRecommendations.indices) {
                recommendations[i].motherboard = motherBoardRecommendations[i]
            }

            val psuRecommendations = this.psuService.getPSURecommendations(budgetAllocation.getValue("power_supply"), recommendations, filterObject)

            if(psuRecommendations.isEmpty()) {
                return listOf()
            }

            for(i in psuRecommendations.indices) {
                recommendations[i].psu = psuRecommendations[i]
            }

            if(filterObject.pcNeedNetworkCard) {
                val networkCardRecommendations =
                    this.networkCardService.getNetworkCardRecommendations(budgetAllocation.getValue("network_card"), filterObject)

                for(i in networkCardRecommendations.indices) {
                    recommendations[i].networkCard = networkCardRecommendations[i]
                }
            }

            return recommendations

    }

    private fun divideBudgetWork(filterObject: PCRequest): MutableMap<String, Double> {
        // The user does not need optional components
        if(!filterObject.pcNeedOpticalDrive && !filterObject.pcNeedNetworkCard) {
            return mutableMapOf(
                "cpu" to (filterObject.pcPrice * 0.24),
                "ram" to (filterObject.pcPrice * 0.17),
                "internal_storage" to (filterObject.pcPrice * 0.15),
                "motherboard" to (filterObject.pcPrice * 0.1),
                "power_supply" to (filterObject.pcPrice * 0.08),
                "case" to (filterObject.pcPrice * 0.06),
                "cpu_fan" to (filterObject.pcPrice * 0.05),
                "fan" to (filterObject.pcPrice * 0.05),
                "wifi_card" to (filterObject.pcPrice * 0.05),
                "sound_card" to (filterObject.pcPrice * 0.05)
            );
        }

        // The user needs an optical drive
        else if(filterObject.pcNeedOpticalDrive && !filterObject.pcNeedNetworkCard) {
            return mutableMapOf(
                "cpu" to (filterObject.pcPrice * 0.23),
                "ram" to (filterObject.pcPrice * 0.16),
                "internal_storage" to (filterObject.pcPrice * 0.14),
                "motherboard" to (filterObject.pcPrice * 0.09),
                "power_supply" to (filterObject.pcPrice * 0.07),
                "case" to (filterObject.pcPrice * 0.06),
                "cpu_fan" to (filterObject.pcPrice * 0.05),
                "fan" to (filterObject.pcPrice * 0.05),
                "wifi_card" to (filterObject.pcPrice * 0.05),
                "sound_card" to (filterObject.pcPrice * 0.05),
                "optical_drive" to (filterObject.pcPrice * 0.05)
            )
        }

        // The user needs a network card
        else if(!filterObject.pcNeedOpticalDrive && filterObject.pcNeedNetworkCard) {
            return mutableMapOf(
                "cpu" to (filterObject.pcPrice * 0.23),
                "ram" to (filterObject.pcPrice * 0.16),
                "internal_storage" to (filterObject.pcPrice * 0.14),
                "motherboard" to (filterObject.pcPrice * 0.09),
                "power_supply" to (filterObject.pcPrice * 0.07),
                "case" to (filterObject.pcPrice * 0.06),
                "cpu_fan" to (filterObject.pcPrice * 0.05),
                "fan" to (filterObject.pcPrice * 0.05),
                "wifi_card" to (filterObject.pcPrice * 0.05),
                "sound_card" to (filterObject.pcPrice * 0.05),
                "network_card" to (filterObject.pcPrice * 0.05)
            )
        }

        // The user needs both an optical drive and a network card
        else {
            return mutableMapOf(
                "cpu" to (filterObject.pcPrice * 0.22),
                "ram" to (filterObject.pcPrice * 0.15),
                "internal_storage" to (filterObject.pcPrice * 0.13),
                "motherboard" to (filterObject.pcPrice * 0.08),
                "power_supply" to (filterObject.pcPrice * 0.06),
                "case" to (filterObject.pcPrice * 0.06),
                "cpu_fan" to (filterObject.pcPrice * 0.05),
                "fan" to (filterObject.pcPrice * 0.05),
                "wifi_card" to (filterObject.pcPrice * 0.05),
                "sound_card" to (filterObject.pcPrice * 0.05),
                "network_card" to (filterObject.pcPrice * 0.05),
                "optical_drive" to (filterObject.pcPrice * 0.05)
            )
        }

    }

    private fun divideBudgetGamingAndPower(filterObject: PCRequest): MutableMap<String, Double> {
        // The user does not need optional components
        if (!filterObject.pcNeedOpticalDrive && !filterObject.pcNeedNetworkCard) {
            return mutableMapOf(
                "gpu" to (filterObject.pcPrice * 0.29),
                "cpu" to (filterObject.pcPrice * 0.2),
                "ram" to (filterObject.pcPrice * 0.1),
                "internal_storage" to (filterObject.pcPrice * 0.15),
                "motherboard" to (filterObject.pcPrice * 0.08),
                "power_supply" to (filterObject.pcPrice * 0.07),
                "case" to (filterObject.pcPrice * 0.03),
                "cpu_fan" to (filterObject.pcPrice * 0.02),
                "fan" to (filterObject.pcPrice * 0.02),
                "wifi_card" to (filterObject.pcPrice * 0.02),
                "sound_card" to (filterObject.pcPrice * 0.02)
            );
        }
        else if(filterObject.pcNeedOpticalDrive && !filterObject.pcNeedNetworkCard) {
            return mutableMapOf(
                "gpu" to (filterObject.pcPrice * 0.28),
                "cpu" to (filterObject.pcPrice * 0.19),
                "ram" to (filterObject.pcPrice * 0.1),
                "internal_storage" to (filterObject.pcPrice * 0.15),
                "motherboard" to (filterObject.pcPrice * 0.08),
                "power_supply" to (filterObject.pcPrice * 0.07),
                "case" to (filterObject.pcPrice * 0.03),
                "cpu_fan" to (filterObject.pcPrice * 0.02),
                "fan" to (filterObject.pcPrice * 0.02),
                "wifi_card" to (filterObject.pcPrice * 0.01),
                "sound_card" to (filterObject.pcPrice * 0.02),
                "optical_drive" to (filterObject.pcPrice * 0.02)
            );
        }

        else if(!filterObject.pcNeedOpticalDrive && filterObject.pcNeedNetworkCard) {
            return mutableMapOf(
                "gpu" to (filterObject.pcPrice * 0.28),
                "cpu" to (filterObject.pcPrice * 0.19),
                "ram" to (filterObject.pcPrice * 0.1),
                "internal_storage" to (filterObject.pcPrice * 0.15),
                "motherboard" to (filterObject.pcPrice * 0.08),
                "power_supply" to (filterObject.pcPrice * 0.07),
                "case" to (filterObject.pcPrice * 0.03),
                "cpu_fan" to (filterObject.pcPrice * 0.02),
                "fan" to (filterObject.pcPrice * 0.02),
                "wifi_card" to (filterObject.pcPrice * 0.02),
                "sound_card" to (filterObject.pcPrice * 0.02),
                "network_card" to (filterObject.pcPrice * 0.02)
            );
        }

        else {
            return mutableMapOf(
                "gpu" to (filterObject.pcPrice * 0.27),
                "cpu" to (filterObject.pcPrice * 0.18),
                "ram" to (filterObject.pcPrice * 0.1),
                "internal_storage" to (filterObject.pcPrice * 0.15),
                "motherboard" to (filterObject.pcPrice * 0.08),
                "power_supply" to (filterObject.pcPrice * 0.07),
                "case" to (filterObject.pcPrice * 0.03),
                "cpu_fan" to (filterObject.pcPrice * 0.02),
                "fan" to (filterObject.pcPrice * 0.02),
                "wifi_card" to (filterObject.pcPrice * 0.02),
                "sound_card" to (filterObject.pcPrice * 0.02),
                "network_card" to (filterObject.pcPrice * 0.02),
                "optical_drive" to (filterObject.pcPrice * 0.02)
            );
        }
    }

    private fun divideBudgetCreative(filterObject: PCRequest): MutableMap<String, Double> {
        if (!filterObject.pcNeedOpticalDrive && !filterObject.pcNeedNetworkCard) {
            return mutableMapOf(
                "gpu" to (filterObject.pcPrice * 0.24),
                "cpu" to (filterObject.pcPrice * 0.25),
                "ram" to (filterObject.pcPrice * 0.13),
                "internal_storage" to (filterObject.pcPrice * 0.12),
                "motherboard" to (filterObject.pcPrice * 0.08),
                "power_supply" to (filterObject.pcPrice * 0.07),
                "case" to (filterObject.pcPrice * 0.03),
                "cpu_fan" to (filterObject.pcPrice * 0.02),
                "fan" to (filterObject.pcPrice * 0.02),
                "wifi_card" to (filterObject.pcPrice * 0.02),
                "sound_card" to (filterObject.pcPrice * 0.02)
            );
        }

        else if (filterObject.pcNeedOpticalDrive && !filterObject.pcNeedNetworkCard) {
            return mutableMapOf(
                "gpu" to (filterObject.pcPrice * 0.23),
                "cpu" to (filterObject.pcPrice * 0.24),
                "ram" to (filterObject.pcPrice * 0.13),
                "internal_storage" to (filterObject.pcPrice * 0.12),
                "motherboard" to (filterObject.pcPrice * 0.08),
                "power_supply" to (filterObject.pcPrice * 0.07),
                "case" to (filterObject.pcPrice * 0.03),
                "cpu_fan" to (filterObject.pcPrice * 0.02),
                "fan" to (filterObject.pcPrice * 0.02),
                "wifi_card" to (filterObject.pcPrice * 0.02),
                "sound_card" to (filterObject.pcPrice * 0.02),
                "optical_drive" to (filterObject.pcPrice * 0.02)
            )
        }

        else if (!filterObject.pcNeedOpticalDrive && filterObject.pcNeedNetworkCard) {
            return mutableMapOf(
                "gpu" to (filterObject.pcPrice * 0.23),
                "cpu" to (filterObject.pcPrice * 0.24),
                "ram" to (filterObject.pcPrice * 0.13),
                "internal_storage" to (filterObject.pcPrice * 0.12),
                "motherboard" to (filterObject.pcPrice * 0.08),
                "power_supply" to (filterObject.pcPrice * 0.07),
                "case" to (filterObject.pcPrice * 0.03),
                "cpu_fan" to (filterObject.pcPrice * 0.02),
                "fan" to (filterObject.pcPrice * 0.02),
                "wifi_card" to (filterObject.pcPrice * 0.02),
                "sound_card" to (filterObject.pcPrice * 0.02),
                "network_card" to (filterObject.pcPrice * 0.02)
            )
        }

        else {
            return mutableMapOf(
                "gpu" to (filterObject.pcPrice * 0.22),
                "cpu" to (filterObject.pcPrice * 0.23),
                "ram" to (filterObject.pcPrice * 0.13),
                "internal_storage" to (filterObject.pcPrice * 0.12),
                "motherboard" to (filterObject.pcPrice * 0.08),
                "power_supply" to (filterObject.pcPrice * 0.07),
                "case" to (filterObject.pcPrice * 0.03),
                "cpu_fan" to (filterObject.pcPrice * 0.02),
                "fan" to (filterObject.pcPrice * 0.02),
                "wifi_card" to (filterObject.pcPrice * 0.02),
                "sound_card" to (filterObject.pcPrice * 0.02),
                "network_card" to (filterObject.pcPrice * 0.02),
                "optical_drive" to (filterObject.pcPrice * 0.02)
            )
        }
    }

    private fun getMinimumSize(lists: List<List<Any>>): Int {
        var minimumSize = 100
        for(list in lists) {
            if(list.size < minimumSize) {
                minimumSize = list.size
            }
        }

        return minimumSize
    }
}
