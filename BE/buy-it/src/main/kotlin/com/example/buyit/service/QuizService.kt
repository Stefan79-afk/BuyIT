package com.example.buyit.service

import com.example.buyit.model.PCRequest
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
    fun quiz(filterObject: PCRequest): List<PCRecommendation> {
        val budgetAllocation: MutableMap<String, Double> = when (filterObject.pcUseCase) {
            "work" -> divideBudgetWork(filterObject)
            "gaming" -> divideBudgetGamingAndPower(filterObject)
            "studio" -> divideBudgetCreative(filterObject)
            "power" -> divideBudgetGamingAndPower(filterObject)
            else -> mutableMapOf()
        }

        val recommendations = mutableListOf(
            PCRecommendation(), PCRecommendation(), PCRecommendation(), PCRecommendation(), PCRecommendation())

        addRecommendation(recommendations, Component.CPU, budgetAllocation.getValue("cpu"), filterObject)
        addRecommendation(recommendations, Component.RAM, budgetAllocation.getValue("ram"), filterObject)
        addRecommendation(recommendations, Component.Internal_Storage, budgetAllocation.getValue("internal_storage"), filterObject)
        addRecommendation(recommendations, Component.Case, budgetAllocation.getValue("case"), filterObject)
        addRecommendation(recommendations, Component.Fan, budgetAllocation.getValue("fan"), filterObject)
        addRecommendation(recommendations, Component.CPU_Fan, budgetAllocation.getValue("cpu_fan"), filterObject)
        addRecommendation(recommendations, Component.Sound_Card, budgetAllocation.getValue("sound_card"), filterObject)
        addRecommendation(recommendations, Component.Wifi_Card, budgetAllocation.getValue("wifi_card"), filterObject)
        addRecommendation(recommendations, Component.Motherboard, budgetAllocation.getValue("motherboard"), filterObject)
        addRecommendation(recommendations, Component.PSU, budgetAllocation.getValue("power_supply"), filterObject)

        if(filterObject.pcUseCase != "work")
            addRecommendation(recommendations, Component.GPU, budgetAllocation.getValue("gpu"), filterObject)

        if(filterObject.pcNeedNetworkCard)
            addRecommendation(recommendations, Component.Network_Card, budgetAllocation.getValue("network_card"), filterObject)

        if(filterObject.pcNeedOpticalDrive)
            addRecommendation(recommendations, Component.Optical_Drive, budgetAllocation.getValue("optical_drive"), filterObject)

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

    private fun addRecommendation(recommendations: MutableList<PCRecommendation>, component: Component, componentBudget: Double, filterObject: PCRequest): Unit{
        val componentRecommendations: MutableList<Any>

        when(component) {
            Component.CPU -> {
                componentRecommendations = this.cpuService.getCPURecommendations(componentBudget, filterObject).toMutableList()
            }

            Component.GPU -> {
                componentRecommendations = this.gpuService.getGPURecommendations(componentBudget, filterObject).toMutableList()
            }

            Component.RAM -> {
                componentRecommendations = this.ramService.getRamRecommendations(componentBudget, filterObject).toMutableList()
            }

            Component.Internal_Storage -> {
                componentRecommendations =
                    this.internalStorageService.getInternalStorageReccommendations(componentBudget, filterObject).toMutableList()
            }

            Component.Motherboard -> {
                componentRecommendations =
                    this.motherboardService.getMotherboardRecommendations(componentBudget, recommendations, filterObject)
                        .toMutableList()
            }

            Component.PSU -> {
                componentRecommendations =
                    this.psuService.getPSURecommendations(componentBudget, recommendations, filterObject).toMutableList()
            }

            Component.Fan -> {
                componentRecommendations = this.fanService.getFanRecommendations(componentBudget, filterObject).toMutableList()
            }

            Component.CPU_Fan -> {
                componentRecommendations = this.cpuFanService.getcpuFanRecommendations(componentBudget, filterObject).toMutableList()
            }

            Component.Sound_Card -> {
                componentRecommendations =
                    this.soundCardService.getSoundCardRecommendations(componentBudget, filterObject).toMutableList()
            }

            Component.Wifi_Card -> {
                componentRecommendations =
                    this.wifiCardService.getWifiCardRecommendations(componentBudget, filterObject).toMutableList()
            }

            Component.Network_Card -> {
                componentRecommendations =
                    this.networkCardService.getNetworkCardRecommendations(componentBudget, filterObject).toMutableList()
            }

            Component.Optical_Drive -> {
                componentRecommendations = this.opticalDriveService.getOpticalDriveRecommendations(componentBudget).toMutableList()
            }

            Component.Case -> {
                componentRecommendations = this.caseService.getCaseRecommendations(componentBudget, filterObject).toMutableList()
            }
        }

        while(componentRecommendations.size > recommendations.size) {
            componentRecommendations.removeLast()
        }

        while(componentRecommendations.size < recommendations.size) {
            recommendations.removeLast()
        }

        when(component) {
            Component.CPU -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].cpu = componentRecommendations[i] as CPU
                }
            }

            Component.GPU -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].gpu = componentRecommendations[i] as GPU
                }
            }

            Component.RAM -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].ram = componentRecommendations[i] as RAM
                }
            }

            Component.Internal_Storage -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].internalStorage = componentRecommendations[i] as InternalStorage
                }
            }

            Component.Motherboard -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].motherboard = componentRecommendations[i] as Motherboard
                }
            }

            Component.PSU -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].psu= componentRecommendations[i] as PSU
                }
            }

            Component.Fan -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].fan= componentRecommendations[i] as Fan
                }
            }

            Component.CPU_Fan -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].cpuFan= componentRecommendations[i] as CPUFan
                }
            }

            Component.Sound_Card -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].soundCard= componentRecommendations[i] as SoundCard
                }
            }

            Component.Wifi_Card -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].wifiCard = componentRecommendations[i] as WifiCard
                }
            }

            Component.Network_Card -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].networkCard = componentRecommendations[i] as NetworkCard
                }
            }

            Component.Optical_Drive -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].opticalDrive = componentRecommendations[i] as OpticalDrive
                }
            }

            Component.Case -> {
                for(i in 0 until componentRecommendations.size) {
                    recommendations[i].case = componentRecommendations[i] as Case
                }
            }
        }

    }

}

private enum class Component {
    CPU,
    GPU,
    RAM,
    PSU,
    Motherboard,
    Fan,
    CPU_Fan,
    Internal_Storage,
    Sound_Card,
    Wifi_Card,
    Network_Card,
    Optical_Drive,
    Case
}
