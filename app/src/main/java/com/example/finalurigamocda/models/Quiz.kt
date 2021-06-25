package com.example.finalurigamocda.models

data class Quiz (
    var id : String = "",
    var title : String = "",
    var email : String = "",
    var questions: MutableMap<String, Question> = mutableMapOf()
)
