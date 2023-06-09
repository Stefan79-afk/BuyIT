package com.example.buyit.controllers

import com.example.buyit.model.Question
import com.example.buyit.service.QuestionService
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity.BodyBuilder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/odata/BuyITService/")
class QuestionController(private val questionService: QuestionService) {

    @CrossOrigin(origins = ["http://localhost:5173"])
    @GetMapping("Questions({questionId})")
    fun getQuestionById(@PathVariable("questionId") questionId: Int): ResponseEntity<Any> {
        try {
            val queryResult: Optional<Question> = questionService.getQuestion(questionId);

            if (queryResult.isEmpty) {
                return ResponseEntity.notFound().build()
            }

            return ResponseEntity.ok(queryResult.get());
        } catch (exception: Exception) {
            return ResponseEntity.badRequest().body(exception.message);
        }
    }
}