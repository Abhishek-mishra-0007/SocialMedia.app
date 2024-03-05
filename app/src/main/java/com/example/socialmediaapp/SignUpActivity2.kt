package com.example.socialmediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.socialmediaapp.databinding.ActivitySignUp2Binding
import com.example.socialmediaapp.models.Userabhi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity2 : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUp2Binding.inflate(layoutInflater)
    }
    lateinit var user: Userabhi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val user=Userabhi()

        binding.signUpbutton.setOnClickListener {
            if (binding.name.editText?.text.toString().equals("") or
                binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
            ) {
                Toast.makeText(
                    this@SignUpActivity2,
                    "Please Fill All Information",
                    Toast.LENGTH_SHORT
                ).show()
            } else {


                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString(),
                ).addOnCompleteListener { result ->

                    if (result.isSuccessful) {
                        user.name = binding.name.editText?.text.toString()
                        user.password = binding.password.editText?.text.toString()
                        user.email = binding.email.editText?.text.toString()
                        Firebase.firestore.collection("user")
                            .document(Firebase.auth.currentUser!!.uid).set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this@SignUpActivity2, "login", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    } else {
                        Toast.makeText(
                            this@SignUpActivity2,
                            result.exception?.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
}