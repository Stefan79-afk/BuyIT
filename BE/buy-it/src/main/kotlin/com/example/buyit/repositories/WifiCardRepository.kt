package com.example.buyit.repositories

import com.example.buyit.model.WifiCard
import org.springframework.data.mongodb.repository.MongoRepository

interface WifiCardRepository: MongoRepository<WifiCard, String> {
}