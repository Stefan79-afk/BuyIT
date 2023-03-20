package com.example.buyit.controllers

import com.example.buyit.model.PCRequest
import com.example.buyit.model.Recommendation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/odata/BuyITService")
class QuizController() {

    @PostMapping("/quiz")
    public fun getRecommendations(@RequestBody quiz: PCRequest): ResponseEntity<List<Recommendation>> {
        return ResponseEntity.noContent().build();
    }
}