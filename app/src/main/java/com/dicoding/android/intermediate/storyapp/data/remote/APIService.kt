package com.dicoding.android.intermediate.storyapp.data.remote

import com.dicoding.android.intermediate.storyapp.data.response.LoginUserResponse
import com.dicoding.android.intermediate.storyapp.data.response.RegisterUserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterUserResponse>

    @FormUrlEncoded
    @POST("/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginUserResponse>
}