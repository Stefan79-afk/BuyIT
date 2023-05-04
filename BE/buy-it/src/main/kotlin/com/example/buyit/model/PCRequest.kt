package com.example.buyit.model

data class PCRequest(
    val isKnowledgeable: Boolean,
    val pcType: String,
    val pcSize: String?,
    val pcCaseColor: String?,
    val pcCasePanel: String?,
    val pcPrice: Double,
    val pcUseCase: String,
    val pcSoundChannel: String,
    val pcNeedNetworkCard: Boolean,
    val pcPowerEfficiency: Boolean?,
    val pcFanNoise: String?,
    val pcNeedOpticalDrive: Boolean,
    val pcWorkMultitask: Boolean?,
    val pcWorkLargeStorage: Boolean?,
    val pcWorkFastStorage: Boolean?,
    val pcWorkFastInternet: Boolean?,
    val pcWorkProgramming: Boolean?,
    val pcWorkVirtualization: Boolean?,
    val pcGamingLatestGames: Boolean?,
    val pcGamingWantStreaming: Boolean?,
    val pcGamingGraphicsOrPerformance: String?,
    val pcGamingLargeStorage: Boolean?,
    val pcGamingRayTracingGPU: Boolean?,
    val pcGamingFastInternet: Boolean?,
    val pcMonitorResolution: String?,
    val pcGamingRefreshRate: String?,
    val pcCreativeMostDemandingTask: String?,
    val pcCreativeMultitask: Boolean?,
    val pcCreativeLargeStorage: Boolean?,
    val pcCreativeStorageSpeed: Boolean?,
    val pcCreativeFastInternet: Boolean?,
    val pcCreativeNoLoadingTimes: Boolean?,
    val pcCreativeGPUAcceleration: Boolean?,
    val pcIntensiveMostDemandingTask: String?,
    val pcIntensiveConstantUse: Boolean?,
    val pcIntensiveMultipleGPUs: Boolean?,
    val pcIntensiveLargeStorage: Boolean?,
    val pcIntensiveBestPerformance: Boolean?,
    val pcIntensiveOverclock: Boolean?,
    val pcIntensiveMultitask: Boolean?,
    val pcFanAmount: Int?
)