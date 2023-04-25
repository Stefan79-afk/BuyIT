package com.example.buyit.model

data class PCReccomendation (
    var cpu: CPU,
    var gpu: GPU?,
    var ram: RAM
) {

    fun PCReccomendation(cpu: CPU, gpu: GPU, ram: RAM) {
        this.cpu = cpu
        this.gpu = gpu
        this.ram = ram
    }
}