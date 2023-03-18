package com.example.buyit.service

import com.example.buyit.model.Question
import com.example.buyit.repositories.QuestionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class QuestionService(private val questionsRepository: QuestionsRepository) {
    @Transactional(readOnly = true)
    fun getQuestion(id: Int): Optional<Question> {
        return this.questionsRepository.findById(id);
    }
}