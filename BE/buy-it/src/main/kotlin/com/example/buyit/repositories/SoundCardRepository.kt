package com.example.buyit.repositories

import com.example.buyit.model.SoundCard
import org.springframework.data.mongodb.repository.MongoRepository

interface SoundCardRepository: MongoRepository<SoundCard, String> {
}