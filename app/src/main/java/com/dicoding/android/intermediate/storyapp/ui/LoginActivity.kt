package com.dicoding.android.intermediate.storyapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.android.intermediate.storyapp.databinding.ActivityLoginBinding
import com.dicoding.android.intermediate.storyapp.repo.UserPreferences
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.AuthViewModel
import com.dicoding.android.intermediate.storyapp.ui.viewmodel.AuthViewModelFactory

val Context.dataStore : DataStore<Preferences> by preferencesDataStore("settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private var username : String = "username"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        val authViewModel : AuthViewModel by viewModels { AuthViewModelFactory.getInstance(this)}
        val preferences = UserPreferences.getInstance(dataStore)
        authViewModel.setPreferences(preferences)

        authViewModel.getUserLoginToken()?.observe(
            this, {
                if (it != "" && it != null) {
                    val storiesIntent = Intent(this, StoriesActivity::class.java).apply {
                        putExtra(StoriesActivity.EXTRA_USER_NAME, username)
                    }
                    startActivity(storiesIntent)
                    finish()
                }
            }
        )

        supportActionBar?.hide()

        val registerText = loginBinding.tvRegisterClick
        registerText.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            registerIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(registerIntent)
        }
        val btnLogin = loginBinding.btnLogin

        btnLogin.setOnClickListener {
            val email = loginBinding.emailLogin.text.toString()
            val password = loginBinding.passwordLogin.text.toString()
            authViewModel.loginUser(email, password, supportFragmentManager)
        }

        authViewModel.getLoginResponseStatus().observe(
            this, {
                if (it.error == false) {
                    if (it.loginResult != null) {
                        authViewModel.saveUserName(it.loginResult.name!!)
                        authViewModel.saveUserToken(it.loginResult.token!!)
                        val storiesIntent = Intent(this, StoriesActivity::class.java).apply {
                            putExtra(StoriesActivity.EXTRA_USER_NAME, username)
                        }
                        startActivity(storiesIntent)
                        finish()
                    }
                }
            }
        )

        authViewModel.isResponseLoaded().observe(
            this, {
                if (it) {
                    loginBinding.loginLoading.visibility = View.VISIBLE
                } else {
                    loginBinding.loginLoading.visibility = View.GONE
                }
            }
        )
    }
}