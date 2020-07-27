package com.kush.educationalapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registeration.*

class Registration : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseFireStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("userDetails")

        firebaseFireStore = FirebaseFirestore.getInstance()

        signUpBtn.setOnClickListener {
            val userName = et_register_name.text.toString()
            val email = et_register_email.text.toString().trim()
            val password = et_register_password.text.toString().trim()
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener { var x = 1 }
                .addOnCompleteListener(this) { task ->
                    run {
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG).show();

                            val userDetails = hashMapOf("UserName" to userName,
                                                        "Email" to email,
                                                        "TimeStamp" to FieldValue.serverTimestamp())
                            firebaseFireStore.collection("userDetails")
                                .add(userDetails)
                                .addOnSuccessListener(this) { documentReference ->
                                    Toast.makeText(this, "Data stored in Firebase FireStore", Toast.LENGTH_LONG).show()
                                }
                                .addOnFailureListener(this) {
                                    Toast.makeText(this, "Error while storing in Firebase FireStore", Toast.LENGTH_LONG).show()
                                }
                        } else {
                            Toast.makeText(this, "Registered UnSuccessfully", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        }
    }

    fun goToLogin(view: View) {
        startActivity(Intent(this, Login::class.java))
    }
}