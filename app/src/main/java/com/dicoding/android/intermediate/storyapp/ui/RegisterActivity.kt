package com.dicoding.android.intermediate.storyapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.dicoding.android.intermediate.storyapp.databinding.ActivityRegisterBinding
import com.dicoding.android.intermediate.storyapp.ui.customview.RegisterResultFragment
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

            authViewModel.getRegisterResponseStatus().observe(
                this, {
                    if (it.error == false) {
                        RegisterResultFragment(true, it.message.toString()).show(
                            fragmentManager,
                            RegisterResultFragment::class.java.simpleName
                        )
                    }
                    if (it.error == true) {
                        RegisterResultFragment(false, it.message.toString()).show(
                            fragmentManager,
                            RegisterResultFragment::class.java.simpleName
                        )
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

        authViewModel.isResponseLoaded().observe(
            this, {
                if (it) {
                    registerBinding.registerLoading.visibility = View.VISIBLE
                } else {
                    registerBinding.registerLoading.visibility = View.GONE
                }
            }
        )
    }
}