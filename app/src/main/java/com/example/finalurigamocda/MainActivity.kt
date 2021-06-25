package com.example.finalurigamocda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import java.util.regex.Matcher
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<TextView>(R.id.alreadyHaveAccount).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val registrationButton = findViewById<Button>(R.id.registerButton)

        registrationButton.setOnClickListener {

            val username = findViewById<EditText>(R.id.registrationUsername).text.toString()
            val email = findViewById<EditText>(R.id.registrationEmail).text.toString()
            val password = findViewById<EditText>(R.id.registrationPassword).text.toString()

            if (username.isEmpty()) {
                Toast.makeText(applicationContext, "enter username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                Toast.makeText(applicationContext, "enter email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                if (!email.toString().trim { it <= ' ' }.matches(emailPattern.toRegex())) {
                    Toast.makeText(applicationContext, "Invalid email address", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            if (password.isEmpty()) {
                Toast.makeText(applicationContext, "enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, ActionActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Registracia Ver Moxerdxa!", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#\$%^&+=])(?=\\S+$).{8,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }
}