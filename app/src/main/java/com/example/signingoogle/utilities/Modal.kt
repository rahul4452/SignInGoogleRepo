package com.example.signingoogle.utilities

class Modal {

    var category: String? = null



    var difficulty: String? = null

    var question: String? = null

    var correctAnswer: String? = null

    var incorrectAnswers: List<String>? = null

    constructor(
        category: String?,

        difficulty: String?,
        question: String?,
        correctAnswer: String?,
        incorrectAnswers: List<String>?
    ) {
        this.category = category
        this.difficulty = difficulty
        this.question = question
        this.correctAnswer = correctAnswer
        this.incorrectAnswers = incorrectAnswers
    }



}