package com.example.buyit.repositories

import com.example.buyit.model.PSU
import org.springframework.data.mongodb.repository.MongoRepository

interface PSURepository: MongoRepository<PSU, String> {
}