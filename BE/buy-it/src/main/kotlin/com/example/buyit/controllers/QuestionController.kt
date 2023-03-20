package com.example.buyit.controllers

import com.example.buyit.model.Question
import com.example.buyit.service.QuestionService
import org.apache.coyote.Response
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
    fun getQuestionById(@PathVariable("questionId") questionId: Int): ResponseEntity<Question> {
        val queryResult: Optional<Question> = questionService.getQuestion(questionId);

        if (queryResult.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(queryResult.get());
    }
}