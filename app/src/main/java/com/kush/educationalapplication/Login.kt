package com.kush.educationalapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        logInBtn.setOnClickListener {
            val userName = et_login_name.text.toString().trim()
            val password = et_login_password.text.toString().trim()

            firebaseAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}