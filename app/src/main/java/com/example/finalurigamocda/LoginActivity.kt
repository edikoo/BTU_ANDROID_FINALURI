package com.example.finalurigamocda

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        findViewById<TextView>(R.id.forgotPassowrd).setOnClickListener {
            val builder =  AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            builder.setView(view)
            val enteredEmail = view.findViewById<EditText>(R.id.forgotPasswordHelp)
            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _->
                if (enteredEmail.text.toString().isNotEmpty()) {
                    mAuth.sendPasswordResetEmail(enteredEmail.text.toString())
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful) {
                                Toast.makeText(this, "Reset Email Sent", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                Toast.makeText(applicationContext, "Email Not Found", android.widget.Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ ->  })
            builder.show()

        }

        findViewById<Button>(R.id.backToRegistration).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()        }

        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {

            val email = findViewById<EditText>(R.id.loginEmail).text.toString()
            val password = findViewById<EditText>(R.id.loginPassword).text.toString()

            if (email.isEmpty()) {
                Toast.makeText(applicationContext, "Enter email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(applicationContext, "Enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, ActionActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Avtorizacia ver moxerxda!", Toast.LENGTH_SHORT).show()
                        }
                    }
        }

    }
}