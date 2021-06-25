package com.example.finalurigamocda

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalurigamocda.adapters.MyQuizAdapter
import com.example.finalurigamocda.adapters.OptionAdapter
import com.example.finalurigamocda.models.Question
import com.example.finalurigamocda.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class StartQuiz : AppCompatActivity() {

    private lateinit var recyclerOptionAdapter: OptionAdapter

    private lateinit var optionRecyclerView: RecyclerView

    private lateinit var description: TextView

    private lateinit var firestore: FirebaseFirestore

    private lateinit var btnPrevious: Button

    private lateinit var btnNext: Button

    private lateinit var btnSubmit: Button

    var questions : MutableList<Question>? = null

    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gson = Gson()
        val quiz = gson.fromJson<Quiz>(intent.getStringExtra("post"), Quiz::class.java)
        getSupportActionBar()?.setTitle(quiz.title);
        setContentView(R.layout.activity_start_quiz)

        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
        btnSubmit = findViewById(R.id.btnSubmit)

        runFirebase(quiz.id)
        setUpEventListener(quiz.title)
    }

    private fun runFirebase(quizId: String) {
        firestore = FirebaseFirestore.getInstance()
        if (quizId != null) {
            firestore.collection("questions").whereEqualTo("quizId", quizId)
                //firestore.collection("questions")
                .get()
                .addOnSuccessListener {
                    if(it != null && !it.isEmpty){
                        questions = it.toObjects(Question::class.java)
                        Log.d("questions", questions.toString())
                        bindViews()
                    }
                }
        }
    }

    private fun setUpEventListener(quizTitle: String) {
        btnPrevious.setOnClickListener {
            index--
            bindViews()
        }

        btnNext.setOnClickListener {
            index++
            bindViews()
        }

        btnSubmit.setOnClickListener {


            var score = 0
            var sumQuestions = questions!!.size

            questions?.forEach {
                if(it.userAnswer == it.answer) {
                    score++
                }

            }

            var builder = AlertDialog.Builder(this)
            builder.setTitle("$quizTitle დასრულდა")
            builder.setMessage("თქვენ დააგროვეთ ${score.toString()} სწორი პასუხი ${sumQuestions.toString()} შეკითხივიდან")
            builder.setNegativeButton("გასაგებია") { dialog, i ->
                dialog.dismiss()
                val intent = Intent(this, ActionActivity::class.java)
                startActivity(intent)
                finish()
            }
            builder.setCancelable(false)
            builder.show()


        }

    }

    private fun bindViews() {
        btnPrevious.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        btnNext.visibility = View.GONE

        if(index == 0){ //first question
            btnNext.visibility = View.VISIBLE
        }
        else if(index == questions!!.size - 1) { // last question
            btnSubmit.visibility = View.VISIBLE
            btnPrevious.visibility = View.VISIBLE
        }
        else{ // Middle
            btnPrevious.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE
        }

        val question = questions?.get(index)

        question?.let {
            description = findViewById(R.id.description)
            description.text = it.description

            val imageView = findViewById<ImageView>(R.id.imageViewQuiz2)

            Glide.with(this)
                .load(it.questionImage)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView)

            optionRecyclerView = findViewById(R.id.optionList)
            recyclerOptionAdapter = OptionAdapter(this, question)
            optionRecyclerView.layoutManager = LinearLayoutManager(this)
            optionRecyclerView.adapter = recyclerOptionAdapter
            optionRecyclerView.setHasFixedSize(true)

        }
    }


}