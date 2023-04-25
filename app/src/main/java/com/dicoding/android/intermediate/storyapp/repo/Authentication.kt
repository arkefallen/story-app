package com.dicoding.android.intermediate.storyapp.repo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.android.intermediate.storyapp.data.remote.APIService
import com.dicoding.android.intermediate.storyapp.data.response.RegisterUserResponse
import com.dicoding.android.intermediate.storyapp.ui.RegisterResultFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Authentication(private val apiService: APIService) {
    private val _registerResponseLiveData = MutableLiveData<RegisterUserResponse>()
    val registerResponse : LiveData<RegisterUserResponse> = _registerResponseLiveData

    private val _nameLiveData = MutableLiveData<String>()
    val name : LiveData<String> = _nameLiveData

    private val _userIDLiveData = MutableLiveData<String>()
    val userId : LiveData<String> = _userIDLiveData

    private val _tokenLiveData = MutableLiveData<String>()
    val token : LiveData<String> = _tokenLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoadingLiveData

    private val _isSuccessLiveData = MutableLiveData<String>()
    val isSuccess : LiveData<String> = _isSuccessLiveData

    fun registerUser(
        name : String,
        email : String,
        password : String
    ) {
        _isLoadingLiveData.value = true
        _isSuccessLiveData.value = LOADING_REGISTER

        val client = apiService.register(name, email, password)

        client.enqueue(
            object : Callback<RegisterUserResponse> {
                override fun onResponse(
                    call: Call<RegisterUserResponse>,
                    response: Response<RegisterUserResponse>
                ) {
                    _isLoadingLiveData.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful) {
                        _isSuccessLiveData.value = SUCCESS_REGISTER
                    } else {
                        _isSuccessLiveData.value = FAIL_REGISTER
                    }
                    Log.e(TAG, "onResponse: ${response.message()}")
                    val registerUserResponse = RegisterUserResponse(
                        responseBody?.error,
                        responseBody?.message
                    )
                    _registerResponseLiveData.value = registerUserResponse
                }

                override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                    _isSuccessLiveData.value = FAIL_REGISTER
                    Log.e(TAG, "onFailure: ${t.message}")
                    val registerUserResponse = RegisterUserResponse(
                        true,
                        t.message
                    )
                    _registerResponseLiveData.value = registerUserResponse
                }
            }
        )
    }

    companion object {
        val TAG = this::class.java.simpleName
        val SUCCESS_REGISTER = "Success Register"
        val LOADING_REGISTER = "Process Register"
        val FAIL_REGISTER = "Failed Register"
        private var instance: Authentication? = null

        fun getInstance(
            apiService: APIService,
        ) : Authentication =
            instance ?: synchronized(this) {
                instance ?: Authentication(apiService)
            }.also { instance = it }
    }
}