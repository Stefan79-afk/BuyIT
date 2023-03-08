package com.example.buyit.repositories

import com.example.buyit.model.InternalStorage
import org.springframework.data.mongodb.repository.MongoRepository

interface InternalStorageRepository: MongoRepository<InternalStorage, String> {
}