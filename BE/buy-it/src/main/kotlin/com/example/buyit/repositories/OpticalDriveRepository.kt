package com.example.buyit.repositories

import com.example.buyit.model.OpticalDrive
import org.springframework.data.mongodb.repository.MongoRepository

interface OpticalDriveRepository: MongoRepository<OpticalDrive, String> {
}