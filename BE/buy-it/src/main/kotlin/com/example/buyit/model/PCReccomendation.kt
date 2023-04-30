package com.example.buyit.model

data class PCReccomendation (
    var cpu: CPU,
    var gpu: GPU?,
    var ram: RAM,
    val internalStorage: InternalStorage
) {

}