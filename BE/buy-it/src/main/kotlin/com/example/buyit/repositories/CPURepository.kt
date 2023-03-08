package com.example.buyit.repositories

import com.example.buyit.model.CPU
import org.springframework.data.mongodb.repository.MongoRepository

interface CPURepository: MongoRepository<CPU, String> {
}