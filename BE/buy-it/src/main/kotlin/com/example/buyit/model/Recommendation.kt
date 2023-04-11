package com.example.buyit.model


data class Recommendation(
    val cpu: CPU,
    val gpu: GPU?,
    val motherboard: Motherboard,
    val psu: PSU,
    val cpuFan: CPUFan,
    val fan: Fan?,
    val internalStorage: InternalStorage,
    val networkCard: NetworkCard?,
    val wifiCard: WifiCard,
    val opticalDrive: OpticalDrive?,
    val ram: RAM,
    val soundCard: SoundCard,
    val case: Case

) {
}