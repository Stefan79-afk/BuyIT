package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("questions")
data class Question(
    @Id val id: Int,
    @Field("question") val question: String,
    @Field("filterProp") val filterProp: String,
    @Field("answers") val answers: Array<Answer>?,
    @Field("value") val value: Any?,
    @Field("branch") val branch: Int?
) {
}

data class Answer(
    @Field("choice") val choice: Int,
    @Field("answer") val answer: String,
    @Field("value") val value: Any?,
    @Field("branch") val branch: Int?
)