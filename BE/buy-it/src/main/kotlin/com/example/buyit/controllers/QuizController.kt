package com.example.buyit.controllers

import com.example.buyit.model.PCRecommendation
import com.example.buyit.model.PCRequest
import com.example.buyit.service.QuizService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/odata/BuyITService")
class QuizController(private val quizService: QuizService) {

    @CrossOrigin(origins = ["http://localhost:5173"])
    @PostMapping("/quiz")
    fun getRecommendations(@RequestBody quiz: PCRequest): ResponseEntity<Any> {
        val recommendations: List<PCRecommendation> = quizService.quiz(quiz);

        if(recommendations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recommendations);
    }
}