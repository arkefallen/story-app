package com.dicoding.android.intermediate.storyapp.ui.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.android.intermediate.storyapp.data.response.LoginUserResponse
import com.dicoding.android.intermediate.storyapp.data.response.RegisterUserResponse
import com.dicoding.android.intermediate.storyapp.repo.Authentication
import com.dicoding.android.intermediate.storyapp.repo.UserPreferences
import kotlinx.coroutines.launch

class AuthViewModel(private val authentication: Authentication) : ViewModel() {
    private var preferences : UserPreferences? = null

    fun getRegisterResponseStatus() : LiveData<RegisterUserResponse> = authentication.registerResponse

    fun getLoginResponseStatus() : LiveData<LoginUserResponse> = authentication.loginResponse

    fun isResponseLoaded() : LiveData<Boolean> = authentication.isLoading

    fun registerUser(
        name: String,
        email: String,
        password: String,
        fragment: FragmentManager
    ) {
        viewModelScope.launch {
            authentication.registerUser(name, email, password,fragment)
        }
    }

    fun loginUser(
        email: String,
        password: String,
        fragment: FragmentManager
    ) {
        viewModelScope.launch {
            authentication.loginUser(email, password, fragment)
        }
    }

    fun setPreferences(pref: UserPreferences) {
        preferences = pref
    }

    fun getUserLoginToken() : LiveData<String>? {
        Log.e(this::class.java.simpleName, "getUserInfo in viewModel token: ${preferences?.getUserToken()?.asLiveData()?.value.toString()}")
        return preferences?.getUserToken()?.asLiveData()
    }

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            preferences?.saveUserToken(token)
        }
    }

    fun getUserLoginName() : LiveData<String>? {
        Log.e(this::class.java.simpleName, "getUserName in viewModel name: ${preferences?.getUserName()?.asLiveData()?.value.toString()}")
        return preferences?.getUserName()?.asLiveData()
    }

    fun saveUserName(name: String) {
        viewModelScope.launch {
            preferences?.saveUserName(name)
        }
    }

    fun clearData() {
        viewModelScope.launch {
            preferences?.clearData()
        }
    }
}