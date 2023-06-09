package com.dicoding.android.intermediate.storyapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.android.intermediate.storyapp.repo.Authentication
import com.dicoding.android.intermediate.storyapp.repo.Injection

class AuthViewModelFactory private constructor(private val authentication: Authentication)
    : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(authentication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        private var instance: AuthViewModelFactory? = null

        fun getInstance() : AuthViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: AuthViewModelFactory(Injection.provideAuth())
            }.also {
                instance = it
            }
    }
}