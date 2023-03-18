package com.example.buyit.controllers

import com.example.buyit.model.Question
import com.example.buyit.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/odata/BuyITService/")
class QuestionController(private val questionService: QuestionService) {

    @GetMapping("Questions({questionId})")
    fun getQuestionById(@PathVariable("questionId") questionId: Int): Question {
        val queryResult: Optional<Question> = questionService.getQuestion(questionId);

        if (queryResult.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Question with id %s not found", questionId));
        }

        return queryResult.get();
    }
}