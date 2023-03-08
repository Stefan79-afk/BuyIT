package com.example.buyit.repositories

import com.example.buyit.model.RAM
import org.springframework.data.mongodb.repository.MongoRepository

interface RAMRepository: MongoRepository<RAM, String> {
}