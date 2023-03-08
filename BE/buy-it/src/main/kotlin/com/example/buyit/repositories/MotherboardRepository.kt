package com.example.buyit.repositories

import com.example.buyit.model.Motherboard
import org.springframework.data.mongodb.repository.MongoRepository

interface MotherboardRepository: MongoRepository<Motherboard, String> {
}