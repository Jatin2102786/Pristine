package com.jatin.pristine.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.jatin.pristine.R
import com.jatin.pristine.activities.MainActivity
import com.jatin.pristine.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        Assigning email and password to SaveStateHandle
        binding.emailEditText.setText(authViewModel.email)
        binding.passwordEditText.setText(authViewModel.password)


//        Changing end icon toggle
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.passwordInputLayout.endIconMode = if (s.isNullOrEmpty()) {
                    TextInputLayout.END_ICON_NONE

                } else {
                    TextInputLayout.END_ICON_PASSWORD_TOGGLE
                }
                authViewModel.password = s.toString()

            }
        })
        binding.emailEditText.addTextChangedListener { authViewModel.email = it.toString() }
//        binding.passwordEditText.addTextChangedListener{authViewModel.password = it.toString()}

        binding.loginButton.setOnClickListener {
            authViewModel.login { success, task ->

                if (success) {
                    Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, task, Toast.LENGTH_SHORT).show()
                }

            }
        }

        binding.signUpButton.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
}