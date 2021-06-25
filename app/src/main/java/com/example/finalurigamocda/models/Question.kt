package com.example.finalurigamocda.models

data class Question(
    var quizId: String = "",
    var description: String = "",
    var questionImage: String = "",
    var option1: String = "",
    var option2: String = "",
    var option3: String = "",
    var answer: String = "",
    var userAnswer: String = ""
)
