package com.example.buyit.repositories

import com.example.buyit.model.GPU
import org.springframework.data.mongodb.repository.MongoRepository

interface GPURepository: MongoRepository<GPU, String> {
}