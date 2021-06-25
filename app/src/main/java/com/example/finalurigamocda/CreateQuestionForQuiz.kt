package com.example.finalurigamocda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.finalurigamocda.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class CreateQuestionForQuiz : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question_for_quiz)

        val gson = Gson()
        val quiz = gson.fromJson<Quiz>(intent.getStringExtra("post"), Quiz::class.java)

        getSupportActionBar()?.setTitle(quiz.title);


        val createQuestionButton = findViewById<Button>(R.id.createQuestionButton)

        createQuestionButton.setOnClickListener {

            val questionDescription = findViewById<EditText>(R.id.quizQuestion)
            val questionImage = findViewById<EditText>(R.id.quizImage)
            val questionAnswer1 = findViewById<EditText>(R.id.quizAnswer1)
            val questionAnswer2 = findViewById<EditText>(R.id.quizAnswer2)
            val questionAnswer3 = findViewById<EditText>(R.id.quizAnswer3)
            val quizCorrectAnswer = findViewById<EditText>(R.id.quizCorrectAnswer)

            if (questionDescription.text.toString().isEmpty() || questionAnswer1.text.toString().isEmpty() || questionAnswer2.text.toString().isEmpty()
                || questionAnswer3.text.toString().isEmpty() || quizCorrectAnswer.text.toString().isEmpty()) {
                Toast.makeText(this, "Sheavset yvela veli", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }

            val question: MutableMap<String, Any> = HashMap()
            question["quizId"] = quiz.id.toString()
            question["description"] = questionDescription.text.toString()
            question["questionImage"] = questionImage.text.toString()
            question["option1"] = questionAnswer1.text.toString()
            question["option2"] = questionAnswer2.text.toString()
            question["option3"] = questionAnswer3.text.toString()
            question["answer"] = quizCorrectAnswer.text.toString()

            firestore = FirebaseFirestore.getInstance()

            firestore.collection("questions")
                .add(question)
                .addOnSuccessListener {
                    Toast.makeText(this, "Shekitxva warmatebit daemata", Toast.LENGTH_SHORT).show()
                    questionDescription.getText().clear()
                    questionImage.getText().clear()
                    questionAnswer1.getText().clear()
                    questionAnswer2.getText().clear()
                    questionAnswer3.getText().clear()
                    quizCorrectAnswer.getText().clear()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Shekitxvis damateba ver moxerxda",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

    }
}