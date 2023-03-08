package com.example.buyit.repositories

import com.example.buyit.model.Question
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface QuestionsRepository: MongoRepository<Question, Int> {
    override
    fun findById(id: Int): Optional<Question>
}