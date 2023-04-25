package com.dicoding.android.intermediate.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.android.intermediate.storyapp.R
import com.dicoding.android.intermediate.storyapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        supportActionBar?.hide()

        val registerText = loginBinding.tvRegisterClick
        registerText.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            registerIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(registerIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        // TODO: Validate user login sessions
    }
}