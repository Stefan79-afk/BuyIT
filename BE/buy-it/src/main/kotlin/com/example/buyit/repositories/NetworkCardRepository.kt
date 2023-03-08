package com.example.buyit.repositories

import com.example.buyit.model.NetworkCard
import org.springframework.data.mongodb.repository.MongoRepository

interface NetworkCardRepository: MongoRepository<NetworkCard, String> {
}