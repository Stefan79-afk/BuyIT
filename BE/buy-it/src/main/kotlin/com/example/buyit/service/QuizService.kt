package com.example.buyit.service

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
    private val upsRepository: UPSRepository,
    private val wifiCardRepository: WifiCardRepository

) {
    fun quiz()
}