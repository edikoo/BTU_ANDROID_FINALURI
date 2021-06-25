package com.example.finalurigamocda.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalurigamocda.R
import com.example.finalurigamocda.adapters.MyQuizAdapter
import com.example.finalurigamocda.models.Quiz
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var recyclerMyQuizAdapter: MyQuizAdapter

    private lateinit var recyclerView: RecyclerView

    private lateinit var firestore: FirebaseFirestore

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerMyQuizAdapter = MyQuizAdapter(setUpFirebase())
        recyclerView.layoutManager = GridLayoutManager(getActivity(), 2)
        recyclerView.adapter = recyclerMyQuizAdapter


    }

    private fun setUpFirebase(): List<Quiz> {
        val quizes = ArrayList<Quiz>()
        val userEmail = mAuth.currentUser?.email.toString()

        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizes")
        collectionReference.addSnapshotListener { value, error ->
            if(value == null || error != null) {
                Toast.makeText(getActivity(), "Ver Xerxdeba monacemebis amogeba", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            quizes.clear()

            for (quiz in value.toObjects(Quiz::class.java)) {
                if(quiz.email == userEmail) {
                    quizes.add(quiz)
                }
            }
            recyclerView.adapter?.notifyDataSetChanged()
        }
        return quizes;

    }




}