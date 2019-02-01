package com.example.signingoogle.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class QuestionResponse {

    @SerializedName("response_code")
    @Expose
    private var responseCode: Int? = null
    @SerializedName("results")
    @Expose
    private var results: List<Result>? = null

    /**
     * No args constructor for use in serialization
     *
     */
    fun QuestionResponse() {}

    /**
     *
     * @param responseCode
     * @param results
     */
    fun QuestionResponse(responseCode: Int?, results: List<Result>) {
        this.responseCode = responseCode
        this.results = results
    }

    fun getResponseCode(): Int? {
        return responseCode
    }

    fun setResponseCode(responseCode: Int?) {
        this.responseCode = responseCode
    }

    fun getResults(): List<Result>? {
        return results
    }

    fun setResults(results: List<Result>) {
        this.results = results
    }

    inner class Result {

        @SerializedName("category")
        @Expose
        var category: String? = null
        @SerializedName("type")
        @Expose
        var type: String? = null
        @SerializedName("difficulty")
        @Expose
        var difficulty: String? = null
        @SerializedName("question")
        @Expose
        var question: String? = null
        @SerializedName("correct_answer")
        @Expose
        var correctAnswer: String? = null
        @SerializedName("incorrect_answers")
        @Expose
        var incorrectAnswers: List<String>? = null

        /**
         * No args constructor for use in serialization
         *
         */
        constructor() {}

        /**
         *
         * @param correctAnswer
         * @param category
         * @param incorrectAnswers
         * @param difficulty
         * @param question
         * @param type
         */
        constructor(
            category: String,
            type: String,
            difficulty: String,
            question: String,
            correctAnswer: String,
            incorrectAnswers: List<String>
        ) : super() {
            this.category = category
            this.type = type
            this.difficulty = difficulty
            this.question = question
            this.correctAnswer = correctAnswer
            this.incorrectAnswers = incorrectAnswers
        }

    }
}