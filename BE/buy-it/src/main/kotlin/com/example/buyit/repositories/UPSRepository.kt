package com.example.buyit.repositories

import com.example.buyit.model.UPS
import org.springframework.data.mongodb.repository.MongoRepository

interface UPSRepository: MongoRepository<UPS, String> {
}