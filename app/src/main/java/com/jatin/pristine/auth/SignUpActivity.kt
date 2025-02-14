package com.jatin.pristine.auth

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jatin.pristine.R
import com.jatin.pristine.activities.MainActivity
import com.jatin.pristine.data_classes.User
import com.jatin.pristine.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        assigning email and password to SaveStateHandle
        binding.emailEditText.setText(authViewModel.email)
        binding.passwordEditText.setText(authViewModel.password)


//        using addTextChangedListener
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                binding.passwordInputLayout.endIconMode = if (s.isNullOrEmpty()) {
                    TextInputLayout.END_ICON_NONE
                } else {
                    TextInputLayout.END_ICON_PASSWORD_TOGGLE
                }
            }

        })
        binding.emailEditText.addTextChangedListener{authViewModel.email = it.toString()}
        binding.passwordEditText.addTextChangedListener{authViewModel.password = it.toString()}


        binding.signUpButton.setOnClickListener {

            val email = binding.emailEditText.text?.toString()
            val password = binding.passwordEditText.text?.toString()

            if (email.isNullOrEmpty()) {
                binding.emailEditText.error = "Please fill this field"
            }else if(password.isNullOrEmpty()) {
                binding.passwordEditText.error = "Please fill this field"
            }
            else {
                authViewModel.createUser { success,task ->
                    if (success) {
//                        addUser(email.toString())
                        Toast.makeText(this, "Account creation successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()

                    }
                    else {
                        Toast.makeText(this, task, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        binding.loginButton.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }


}