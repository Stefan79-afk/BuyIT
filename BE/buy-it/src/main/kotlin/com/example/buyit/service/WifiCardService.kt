package com.example.buyit.service

import com.example.buyit.model.PCRequest
import com.example.buyit.model.WifiCard
import com.example.buyit.repositories.WifiCardRepository
import org.springframework.stereotype.Service

@Service
class WifiCardService (
    private val wifiCardRepository: WifiCardRepository
){
}