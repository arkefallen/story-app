package com.dicoding.android.intermediate.storyapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.android.intermediate.storyapp.data.response.RegisterUserResponse
import com.dicoding.android.intermediate.storyapp.repo.Authentication
import kotlinx.coroutines.launch

class AuthViewModel(private val authentication: Authentication) : ViewModel() {

    fun getResponseStatus() : LiveData<RegisterUserResponse> = authentication.registerResponse

    fun registerUser(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            authentication.registerUser(name, email, password)
        }
    }
}