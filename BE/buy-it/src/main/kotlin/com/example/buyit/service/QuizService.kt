package com.example.buyit.service

import com.example.buyit.model.PCRequest
import com.example.buyit.repositories.*
import org.springframework.stereotype.Service

@Service
class QuizService(
    private val caseRepository: CaseRepository,
    private val cpuFanRepository: CPUFanRepository,
    private val cpuRepository: CPURepository,
    private val fanRepository: FanRepository,
    private val gpuRepository: GPURepository,
    private val internalStorageRepository: InternalStorageRepository,
    private val motherboardRepository: MotherboardRepository,
    private val networkCardRepository: NetworkCardRepository,
    private val opticalDriveRepository: OpticalDriveRepository,
    private val psuRepository: PSURepository,
    private val ramRepository: RAMRepository,
    private val soundCardRepository: SoundCardRepository,
    private val wifiCardRepository: WifiCardRepository

) {

    companion object {
        val optionalComponents = arrayListOf<String>("optical_drive", "network_card")
    }
    fun quiz(filterObject: PCRequest): Any {
        var budgetAllocation: MutableMap<String, Double>;
        when (filterObject.pcUseCase) {
            "work" -> budgetAllocation = divideBudgetWork(filterObject);
        }

        return 1;
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
                "sound_card" to (filterObject.pcPrice * 0.05),
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
                "optical_drive" to (filterObject.pcPrice * 0.05),
            )
        }

    }
}
