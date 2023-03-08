package com.example.buyit.repositories

import com.example.buyit.model.Fan
import org.springframework.data.mongodb.repository.MongoRepository

interface FanRepository: MongoRepository<Fan, String> {
}