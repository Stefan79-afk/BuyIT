package com.example.buyit.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("questions")
data class Question(
    @Id val id: Int,
    @Field("question") val question: String,
    @Field("filterProp") val filterProp: String,
    @Field("answers") val answers: Array<Answer>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (id != other.id) return false
        if (question != other.question) return false
        if (filterProp != other.filterProp) return false
        if (!answers.contentEquals(other.answers)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + question.hashCode()
        result = 31 * result + filterProp.hashCode()
        result = 31 * result + answers.contentHashCode()
        return result
    }
}

data class Answer(
    @Field("choice") val choice: Int,
    @Field("answer") val answer: String,
    @Field("value") val value: Any?,
    @Field("branch") val branch: Int
)