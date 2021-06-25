package com.example.finalurigamocda.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.finalurigamocda.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CreateQuizFragment : Fragment(R.layout.fragment_create_quiz) {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var firestore: FirebaseFirestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val createQuizButton = view.findViewById<Button>(R.id.createQuizButton)

        createQuizButton.setOnClickListener {

            val getQuizTitle = view.findViewById<EditText>(R.id.getQuizTitle).text.toString()

            val userEmail = mAuth.currentUser?.email.toString()

            if (getQuizTitle.isEmpty()) {
                Toast.makeText(getActivity(), "Enter Quiz Title", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }

            firestore = FirebaseFirestore.getInstance()

            val quiz: MutableMap<String, Any> = HashMap()
            val tsLong = System.currentTimeMillis() / 1000
            val ts = tsLong.toString()
            quiz["id"] = ts
            quiz["title"] = getQuizTitle
            quiz["email"] = userEmail

            firestore.collection("quizes")
                .add(quiz)
                .addOnSuccessListener {
                    view.findViewById<EditText>(R.id.getQuizTitle).getText().clear()
                    Toast.makeText(getActivity(), "Qvizi warmatebit daemata", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        getActivity(),
                        "Qvizis damateba ver moxerxda",
                        Toast.LENGTH_SHORT
                    ).show()
                }


        }
    }
}