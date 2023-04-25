package com.dicoding.android.intermediate.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.android.intermediate.storyapp.databinding.ActivityRegisterBinding
import com.dicoding.android.intermediate.storyapp.repo.Authentication
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.AuthViewModel
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.AuthViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        supportActionBar?.hide()
        
        val authViewModel : AuthViewModel by viewModels { AuthViewModelFactory.getInstance(this)}

        val btnRegister = registerBinding.btnRegister
        btnRegister.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val name = registerBinding.nameRegister.text.toString()
            val email = registerBinding.emailRegister.text.toString()
            val password = registerBinding.passwordRegister.text.toString()
            authViewModel.registerUser(name, email, password)

            authViewModel.getResponseStatus().observe(
                this, {
                    if (it.error == false) {
                        RegisterResultFragment(true, it.message!!).show(fragmentManager, RegisterResultFragment::class.java.simpleName)
                    } else if (it.error!!) {
                        RegisterResultFragment(false, it.message!!).show(fragmentManager, RegisterResultFragment::class.java.simpleName)
                    }
                }
            )

        }

        val loginText = registerBinding.tvLoginClick
        loginText.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(loginIntent)
        }
    }
}