package com.example.finalurigamocda.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalurigamocda.R
import com.example.finalurigamocda.models.Quiz
import android.content.Intent
import com.example.finalurigamocda.CreateQuestionForQuiz
import com.example.finalurigamocda.StartQuiz
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

class MyQuizAdapter(private val list: List<Quiz>) : RecyclerView.Adapter<MyQuizAdapter.RecycleViewHolder>() {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         val quizTitle: TextView = itemView.findViewById(R.id.quizTitle)

        fun bindQuiz(quiz: Quiz) {
            quizTitle.text = quiz.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        return RecycleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        holder.bindQuiz(list[position])

        holder.itemView.setOnClickListener {
                v: View -> Unit

            val userEmail = mAuth.currentUser?.email.toString()
            if(userEmail == list[holder.position].email) {
                val intent = Intent(v.context, CreateQuestionForQuiz::class.java)
                val gson = Gson()
                intent.putExtra("post", gson.toJson(list[holder.position]))
                v.context.startActivity(intent)
            } else {
                val intent = Intent(v.context, StartQuiz::class.java)
                val gson = Gson()
                intent.putExtra("post", gson.toJson(list[holder.position]))
                v.context.startActivity(intent)
            }

        }
    }

    override fun getItemCount() = list.size


}

