package com.example.buyit.repositories

import com.example.buyit.model.CPUFan
import org.springframework.data.mongodb.repository.MongoRepository

interface CPUFanRepository: MongoRepository<CPUFan, String> {
}