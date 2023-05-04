package com.example.buyit.model


data class Recommendation(
    val cpu: CPU = CPU(),
    val gpu: GPU? = null,
    val motherboard: Motherboard = Motherboard(),
    val psu: PSU = PSU(),
    val cpuFan: CPUFan = CPUFan(),
    val fan: Fan = Fan(),
    val internalStorage: InternalStorage = InternalStorage(),
    val networkCard: NetworkCard? = null,
    val wifiCard: WifiCard = WifiCard(),
    val opticalDrive: OpticalDrive? = null,
    val ram: RAM = RAM(),
    val soundCard: SoundCard = SoundCard(),
    val case: Case = Case()

) {
}