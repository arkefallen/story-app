package com.dicoding.android.intermediate.storyapp.repo

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.android.intermediate.storyapp.data.remote.APIService
import com.dicoding.android.intermediate.storyapp.data.response.LoginUserResponse
import com.dicoding.android.intermediate.storyapp.data.response.RegisterUserResponse
import com.dicoding.android.intermediate.storyapp.ui.customview.LoginResultFragment
import com.dicoding.android.intermediate.storyapp.ui.customview.RegisterResultFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Authentication(private val apiService: APIService) {

    private val _registerResponseLiveData = MutableLiveData<RegisterUserResponse>()
    val registerResponse : LiveData<RegisterUserResponse> = _registerResponseLiveData

    private val _loginResponseLiveData = MutableLiveData<LoginUserResponse>()
    val loginResponse : LiveData<LoginUserResponse> = _loginResponseLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoadingLiveData

    fun registerUser(
        name : String,
        email : String,
        password : String,
        fragment: FragmentManager
    ) {
        _isLoadingLiveData.value = true

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
                        Log.e(TAG, "onResponse: ${response.message()}")
                        RegisterResultFragment(false, response.message()).show(
                            fragment,
                            RegisterResultFragment::class.java.simpleName
                        )
                    } else {
                        RegisterResultFragment(true, response.message()).show(
                            fragment,
                            RegisterResultFragment::class.java.simpleName
                        )
                    }
                    val registerUserResponse = RegisterUserResponse(
                        responseBody?.error,
                        responseBody?.message
                    )
                    _registerResponseLiveData.value = registerUserResponse
                }

                override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                    RegisterResultFragment(false, t.message.toString()).show(
                        fragment,
                        RegisterResultFragment::class.java.simpleName
                    )
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

    fun loginUser(
        email: String,
        password: String,
        fragment: FragmentManager
    ) {
        _isLoadingLiveData.value = true

        val client = apiService.login(email, password)

        client.enqueue(
            object : Callback<LoginUserResponse> {
                override fun onResponse(
                    call: Call<LoginUserResponse>,
                    response: Response<LoginUserResponse>
                ) {
                    _isLoadingLiveData.value = false
                    val responseBody = response.body()
                    Log.e(TAG, "onResponse: ${response.message()}")
                    if (response.isSuccessful == false) {
                        LoginResultFragment(responseBody?.message.toString()).show(fragment, LoginResultFragment::class.java.simpleName)
                    }
                    val loginUserResponse = LoginUserResponse(
                        responseBody?.loginResult,
                        responseBody?.error,
                        responseBody?.message
                    )
                    _loginResponseLiveData.value = loginUserResponse
                }

                override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
                    RegisterResultFragment(false, t.message.toString()).show(
                        fragment,
                        RegisterResultFragment::class.java.simpleName
                    )
                    Log.e(TAG, "onFailure: ${t.message}")
                    val loginUserResponse = LoginUserResponse(
                        null,
                        true,
                        t.message
                    )
                    _loginResponseLiveData.value = loginUserResponse
                }
            }
        )
    }

    companion object {
        val TAG = this::class.java.simpleName
        private var instance: Authentication? = null

        fun getInstance(
            apiService: APIService,
        ) : Authentication =
            instance ?: synchronized(this) {
                instance ?: Authentication(apiService)
            }.also { instance = it }
    }
}