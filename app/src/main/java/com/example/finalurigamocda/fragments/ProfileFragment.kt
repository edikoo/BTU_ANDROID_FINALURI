package com.example.finalurigamocda.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.finalurigamocda.ActionActivity
import com.example.finalurigamocda.LoginActivity
import com.example.finalurigamocda.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var myEmail: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myEmail = view.findViewById(R.id.myEmail)

        myEmail.text = mAuth.currentUser?.email.toString()

        val updatePasswordButton = view.findViewById<Button>(R.id.updatePasswordButton)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        updatePasswordButton.setOnClickListener {

            val currentPassword = view.findViewById<EditText>(R.id.currentPassword).text.toString()
            val newPassword = view.findViewById<EditText>(R.id.newPasswrd).text.toString()

            if (currentPassword.isEmpty()) {
                Toast.makeText(getActivity(), "Enter current password", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            if (newPassword.isEmpty()) {
                Toast.makeText(getActivity(), "Enter new password", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }

            val credentials = EmailAuthProvider.getCredential(mAuth.currentUser?.email.toString(), currentPassword)

            mAuth.currentUser?.reauthenticate(credentials)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(getActivity(), "Re avtorizacia warmatebit shesrulda!", Toast.LENGTH_SHORT).show()
                        mAuth.currentUser?.updatePassword(newPassword)
                            ?.addOnCompleteListener { task ->
                                if(task.isSuccessful) {
                                    Toast.makeText(getActivity(), "Paroli Warmatebit Sheicvala!", Toast.LENGTH_SHORT).show()
                                    mAuth.signOut()
                                    val intent = Intent (getActivity(), LoginActivity::class.java)
                                    startActivity(intent)
                                    getActivity()?.finish();
                                }
                            }
                    } else {
                        Toast.makeText(getActivity(), "Mimdinare paroli arasworia!", Toast.LENGTH_SHORT).show()
                    }
                }


        }

        logoutButton.setOnClickListener {
            mAuth.signOut()
            val intent = Intent (getActivity(), LoginActivity::class.java)
            startActivity(intent)
            getActivity()?.finish();
        }

    }
}