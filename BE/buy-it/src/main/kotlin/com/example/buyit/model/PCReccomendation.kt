package com.example.buyit.model

data class PCReccomendation (
    var cpu: CPU = CPU(),
    var gpu: GPU? = null,
    var ram: RAM = RAM(),
    var internalStorage: InternalStorage = InternalStorage(),
    var case: Case = Case(),
    var motherboard: Motherboard = Motherboard(),
    var psu: PSU = PSU(),
    var soundCard: SoundCard = SoundCard(),
    var wifiCard: WifiCard = WifiCard(),
    var fans: List<Fan> = mutableListOf(),
    var cpuFan: CPUFan = CPUFan(),
    var networkCard: NetworkCard? = null,
    var opticalDrive: OpticalDrive? = null
) {

}